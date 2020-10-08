package com.example.expenceappmvvm.screens.actions

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_resources.screens.action.adapters.ActionsAdapter
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.databinding.ActivityActionBinding
import com.example.expenceappmvvm.domain.util.DateAndTimeUtils
import com.example.expenceappmvvm.domain.util.extensions.toast
import kotlinx.android.synthetic.main.activity_action.*
import kotlinx.android.synthetic.main.toolbar_back_arrow.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ActionActivity : AppCompatActivity() {
    private val viewModel: ActionViewModel by viewModel()
    private var calendarDate = ""
    private var timePicker = ""
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)
        DataBindingUtil.setContentView<ActivityActionBinding>(this, R.layout.activity_action)
            .apply {
                lifecycleOwner = this@ActionActivity
                actionViewModel = viewModel
            }
        initObservers()
        initAdapter()
        initCalendar()
        initTimePicker()
        enableSaveButtonVisibility()
    }

    private fun initObservers() {
        viewModel.addImage.observe(this, Observer {
            action_delete_pdf.visibility = TextView.VISIBLE
            pickImage() //add image to activity + storeImageInFiles
        })

        viewModel.action.observe(this, Observer {
            Log.d("it is ", it.toString())
            if (actionValidated()) {
                viewModel.insertActionInDB(it)
                this.toast("Action added to db")
                finish()
            }
        })

        viewModel.clickDelete.observe(this, Observer {
            action_delete_pdf.setImageURI(null)
        })

        viewModel.clickSave.observe(this, Observer {
            viewModel.action.value = initAction()
        })
    }

    private fun actionValidated(): Boolean {
        if (viewModel.action.value?.amount != 0.0)
            return true
        return false
    }

    fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        ActivityCompat.startActivityForResult(this, intent, IMAGE_PICK_CODE, null)
    }

    private fun storeImageInFiles(imageView: ImageView) {
        val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File(dataDir, "files" + File.separator + "Images")
        } else {
            File("")
        }
        if (!path.exists()) {
            path.mkdirs()
        } else {
            val drawable: Drawable? = imageView.drawable ?: return
            val bitmap = (drawable as BitmapDrawable?)?.bitmap ?: return
            //write to file
            //TODO: change image_pdf to id_pdf according to the last entry in the db
            val outFile = File(path, "image_pdf" + ".jpeg")
            val stream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            imageView.setImageURI(Uri.parse(outFile.toString()))
        }
    }

    private fun initAction(): Action {
        return Action().apply {
            //TODO: action.userId -> se ia automat din baza de date, dar deocamadata il lasam hard codat
            userId = 1
//            amount = toDouble(viewModel.amount.value.toString())
            date = DateAndTimeUtils.convertFromStringToDate(calendarDate, timePicker)
            category = viewModel.category.value + ""
            details = viewModel.details.value + ""
            if (action_details_pdf.drawable != null)
            //TODO: change image_pdf to id_pdf according to the last entry in the db
                picturePath = "image_pdf"
        }
    }

    private fun initCalendar() {
        val calendar = Calendar.getInstance()
        calendarDate = DateAndTimeUtils.convertSimpleDate(calendar.time).toString()
        action_calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val date = DateAndTimeUtils.convertSimpleDate(calendar.time)
            calendarDate = date.toString()
        }
    }

    private fun initTimePicker() {
        val time = action_time
        timePicker = "12:00"
        time.setOnTimeChangedListener { v, hour, minute ->
            timePicker = "$hour:$minute"
        }
    }

    private fun initAdapter() {
        action_recycler_view.apply {
            layoutManager =
                GridLayoutManager(this@ActionActivity, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = ActionsAdapter(viewModel.passRecyclerData(), viewModel)
        }
    }

    private fun enableSaveButtonVisibility() {
        toolbar_save.visibility = TextView.VISIBLE
    }

    private fun toDouble(value: String): Double {
        if (value.isEmpty() || value == "" || value == "null") {
            return 0.0
        }
        return value.toDouble()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            action_details_pdf.setImageURI(data?.data)
            storeImageInFiles(action_details_pdf)//store image in phone files
        }
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, ActionActivity::class.java))
        }

        const val IMAGE_PICK_CODE = 1000
    }
}