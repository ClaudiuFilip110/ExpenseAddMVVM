package com.example.expenceappmvvm.screens.expenses

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityAddExpensesBinding
import com.example.expenceappmvvm.domain.binding.converters.BindingConverters
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.screens.expenses.adapter.ExpenseCategoriesAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_add_expenses.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.ext.android.get
import java.util.*

class AddExpensesActivity : AppCompatActivity() {
    private val viewModel: AddExpensesViewModel = get()
    private val permissions: RxPermissions = RxPermissions(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityAddExpensesBinding>(
            this,
            R.layout.activity_add_expenses
        ).apply {
            toolbarScreenTitle = getString(R.string.my_expense)
            converter = BindingConverters()
            viewModel = this@AddExpensesActivity.viewModel
            lifecycleOwner = this@AddExpensesActivity
        }
        initCategoriesItemsAdapter()
        initToolbar()
        initObservers()
    }

    private fun initObservers() {
        viewModel.showDateTimePicker.observe(this, Observer {
            initDateTimeListener()
        })

        viewModel.showRequestPermissionsDialog.observe(this, Observer {
            permissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).subscribe { granted ->
                if (granted) {
                    initAlertBuilder()
                } else {
                    Toast.makeText(this, R.string.permissions_required, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

        viewModel.shouldRedirectToMain.observe(this, Observer {
            finish()
        })
    }

    private fun initAlertBuilder() {
        AlertDialog.Builder(this).apply {
            setItems(Constants.options) { dialogInterface, item ->
                when (Constants.options[item]) {
                    Constants.TAKE_PHOTO -> openCamera()
                    Constants.GALLERY -> openPhotoGallery()
                    Constants.NONE -> dialogInterface.dismiss()
                }
            }
        }.show()
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, Constants.REQUEST_TAKE_PHOTO)
    }

    private fun openPhotoGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, Constants.REQUEST_CHOOSE_PHOTO)
    }

    private fun initDateTimeListener() {
        val calendar = Calendar.getInstance()
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.run {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.run {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
                viewModel.expense.value!!.expenseDate = calendar.timeInMillis
            }.run {
                TimePickerDialog(
                    this@AddExpensesActivity,
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()
            }
        }.run {
            DatePickerDialog(
                this@AddExpensesActivity,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initCategoriesItemsAdapter() {
        categoryRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 4)
            adapter = ExpenseCategoriesAdapter(viewModel.getAdapterItems(), viewModel)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, data, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, AddExpensesActivity::class.java))
        }
    }
}