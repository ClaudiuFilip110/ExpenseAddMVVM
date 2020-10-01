package com.example.expenceappmvvm.screens.expenses

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.repository.ExpensesRepository
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.domain.CategoryEnum
import com.example.expenceappmvvm.domain.binding.models.ExpensesObservableViewModel
import com.example.expenceappmvvm.domain.models.CategoryModel
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class AddExpensesViewModel(
    private val preferences: PreferencesService,
    private val rxSchedulers: AppRxSchedulers,
    private val expensesRepo: ExpensesRepository,
    private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val expense = MutableLiveData<ExpensesObservableViewModel>().apply {
        value = ExpensesObservableViewModel().apply {
            category = CategoryEnum.INCOME.getStringValue()
        }
    }
    val invalidAmount = MutableLiveData<Boolean>().apply { value = false }
    val invalidDate = MutableLiveData<Boolean>().apply { value = false }
    val showRequestPermissionsDialog = SingleLiveEvent<Any>()
    val showDateTimePicker = SingleLiveEvent<Any>()
    val shouldRedirectToMain = SingleLiveEvent<Any>()

    private fun getExpenseValue(): ExpensesObservableViewModel {
        return expense.value!!
    }

    private fun validateTransaction(): Boolean {
        if (getExpenseValue().amount <= 0) {
            invalidAmount.value = true
        }
        if (getExpenseValue().expenseDate > System.currentTimeMillis()) {
            invalidDate.value = true
        }

        return invalidAmount.value!! && invalidDate.value!!
    }

    private fun saveReceivedImage(image: Bitmap, context: Context) {
        getOutputMediaFile(context)?.let {
            try {
                val fos = FileOutputStream(it)
                image.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.close()
            } catch (e: FileNotFoundException) {
                Timber.e(e.message)
            } catch (e: IOException) {
                Timber.e(e.message)
            }
        }
    }

    private fun getOutputMediaFile(context: Context): File? {
        val mediaStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageName = "ME_" + System.currentTimeMillis()
        val image = File.createTempFile(imageName, ".jpg", mediaStorageDir)

        expense.value?.apply {
            picturePath = image.absolutePath
            showRemovePicture = true
        }
        return image
    }

    fun onDateTimeClick() {
        showDateTimePicker.call()
    }

    fun onPictureIconPress() {
        showRequestPermissionsDialog.call()
    }

    fun saveUserTransaction() {
        if (validateTransaction()) {
            return
        }
        preferences.getUserID()?.let { id ->
            expense.value?.run {
                userId = id
                expensesRepo.saveExpense(this.getExpenseEntity())
                shouldRedirectToMain.call()
            }
        }
    }

    fun getAdapterItems(): MutableList<CategoryModel> {
        val categoryItems = mutableListOf<CategoryModel>()
        CategoryEnum.values().forEach {
            categoryItems.add(
                CategoryModel(
                    it.getStringValue(),
                    it.getCategoryIcon(),
                    it == (CategoryEnum.INCOME)
                )
            )
        }
        return categoryItems
    }

    fun onActivityResult(requestCode: Int, data: Intent?, context: Context) {
        when (requestCode) {
            Constants.REQUEST_TAKE_PHOTO -> {
                val image = data?.extras?.get(Constants.DATA) as Bitmap
                saveReceivedImage(image, context)
            }
            Constants.REQUEST_CHOOSE_PHOTO -> {
                val selectedImage = data?.data as Uri
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                context.contentResolver.query(selectedImage, filePathColumn, null, null, null)
                    ?.let {
                        it.moveToFirst()
                        expense.value?.apply {
                            picturePath = it.getString(it.getColumnIndex(filePathColumn[0]))
                            showRemovePicture = true
                        }
                        it.close()
                    }
            }
        }
    }

    fun clear() {
        compositeDisposable.clear()
    }
}