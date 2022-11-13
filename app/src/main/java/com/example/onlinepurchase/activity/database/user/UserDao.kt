package com.example.onlinepurchase.activity.database.user

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     suspend fun insertUser(userEntity: UserEntity)

    @Delete
     suspend fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * from user_table WHERE id = :id")
     suspend fun getUserById(id: Int): UserEntity

    @Query("SELECT * from user_table WHERE email = :email AND password = :password")
     suspend fun connectUser(email: String, password: String): UserEntity

    @Query("UPDATE user_table SET email = :email WHERE id = :id")
     suspend fun updateUserEmail(id: Int, email: String)

    @Query("UPDATE user_table SET address = :address WHERE id = :id")
     suspend fun updateUserAddress(id: Int, address: String)

    @Query("UPDATE user_table SET phone = :phone WHERE id = :id")
     suspend fun updateUserPhone(id: Int, phone: String)

}