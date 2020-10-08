package com.example.expenceappmvvm.screens.actions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.CategoryEnum
import com.example.expenceappmvvm.domain.bindings.setImageUrl
import com.example.expenceappmvvm.domain.models.CategoryModel
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList

class ActionViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {
    val action = MutableLiveData<Action>()
    val amount = MutableLiveData<String>()
    val category = MutableLiveData<String>().apply { value = CategoryEnum.INCOME.getStringValue() }
    val details = MutableLiveData<String>()
    val addImage = SingleLiveEvent<Boolean>()
    val clickSave = SingleLiveEvent<Boolean>()
    val clickDelete = SingleLiveEvent<Boolean>()

    fun onClickSave() {
        clickSave.call()
    }

    fun clickOnAddImage() {
        addImage.call()
    }

    fun clickOnDeleteImage() {
        clickDelete.call()
    }

    fun passRecyclerData(): ArrayList<CategoryModel> {
        val list = ArrayList<CategoryModel>()
        for (cat in CategoryEnum.values()) {
            list.add(
                CategoryModel(
                    cat.getStringValue(),
                    cat.getCategoryIcon(),
                    cat.getStringValue() == "Income"
                )
            )
        }
        return list
    }

    fun insertActionInDB(action: Action) {
        userRepository.insertAction(action)
    }
}