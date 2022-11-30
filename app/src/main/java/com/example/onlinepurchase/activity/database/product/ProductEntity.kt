package com.example.onlinepurchase.activity.database.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.onlinepurchase.activity.data.Category

@Entity(tableName = "product_table")
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val description: String? = null,
    val price: Double? = null,
    val cover: String,
    val promoted: Boolean = false,
    val type: Int, //If 1->Category Title, if 2->Product
    val category: Category
)

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("Drop table product_table")
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `product_table` (`cover` TEXT NOT NULL, `id` INTEGER , `name` TEXT NOT NULL, `description` TEXT, `price` REAL, `promoted` INTEGER NOT NULL, `type` INTEGER NOT NULL, `category` TEXT NOT NULL, " +
                    "PRIMARY KEY(`id`))"
        )
    }
}
