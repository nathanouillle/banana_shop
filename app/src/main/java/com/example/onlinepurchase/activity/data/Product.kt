package com.example.onlinepurchase.activity.data

import com.example.onlinepurchase.activity.database.ProductEntity

var productsList = mutableListOf<Product>()


data class Product(

    val name: String,
    val description: String? = null,
    val price: Double? = null,
    val cover: Int,
    val promoted: Boolean = false,
    val type: Int, //If 1->Category Title, if 2->Product
    val category: Category,
    val id: Int? = productsList.size

    ) {
    //override fun toString(): String ="$category: $name, $price\n"

    companion object{
        fun fromProductEntity(productEntity: ProductEntity): Product {
            return Product(
                name = productEntity.name,
                description = productEntity.description,
                price = productEntity.price,
                cover = productEntity.cover,
                promoted = productEntity.promoted,
                type = productEntity.type,
                category = productEntity.category,
                id = productEntity.id
            )
        }
    }
}
    // Not able to parcelize Category which is a Enum
/*: Parcelable {

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readBoolean(),
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        if (price != null) {
            parcel.writeDouble(price)
        }
        parcel.writeInt(type)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
    }*/