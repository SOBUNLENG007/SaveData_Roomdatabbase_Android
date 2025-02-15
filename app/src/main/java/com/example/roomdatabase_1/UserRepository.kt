package com.example.roomdatabase_1

import com.example.roomdatabase_1.data.User
import com.example.roomdatabase_1.data.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    fun getAllUser(): Flow<List<User>> = userDao.getAllUser()

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
}