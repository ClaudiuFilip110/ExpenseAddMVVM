package com.example.expenceappmvvm.domain.models

import com.example.expenceappmvvm.domain.CategoryEnum

data class RecyclerModel(
    var day: String = "Yesterday",
    var amount: String = "100.0",
    var category: CategoryModel = CategoryModel(
        CategoryEnum.FOOD.getStringValue(),
        CategoryEnum.FOOD.getCategoryIcon(),
        false
    ),
    var totalAmount: String = "1080.0"
)