package com.example.onlinepurchase.activity.database.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.database.product.ProductEntity
import com.example.onlinepurchase.activity.database.user.UserEntity
import java.util.*

@Entity(tableName = "order_table",
    foreignKeys = [ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.SET_NULL)])
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val products: List<Product>,
    val nbProduct: Int = products.size,
    val price: Double,
    val date: Date = Date(),
    val address: String,
    @ColumnInfo(index = true)
    val userId: Int? = null
)

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `order_table` (`id` INTEGER , `products` TEXT NOT NULL, `nbProduct` INTEGER NOT NULL, `price` REAL NOT NULL, `date` INTEGER NOT NULL, `address` TEXT NOT NULL, `userId` INTEGER, " +
                "PRIMARY KEY(`id`), FOREIGN KEY(`userId`) REFERENCES `user_table`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL)")
        database.execSQL("CREATE INDEX IF NOT EXISTS `index_order_table_userId` ON `order_table` (`userId`)")
    }
}
