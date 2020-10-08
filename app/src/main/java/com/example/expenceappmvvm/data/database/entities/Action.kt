package com.example.expenceappmvvm.data.database.entities

import android.os.Parcelable
import android.renderscript.BaseObj
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.expenceappmvvm.data.database.converters.DateConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )]
)
@TypeConverters(DateConverter::class)
data class Action(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userId: Long = 0,
    var amount: Double = 0.0,
    @TypeConverters(DateConverter::class)
    var date: Date = Date(),
    var category: String = "",
    var details: String = "",
    var picturePath: String = ""
)