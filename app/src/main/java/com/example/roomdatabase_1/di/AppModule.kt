package com.example.roomdatabase_1.di

import androidx.room.Room
import com.example.roomdatabase_1.UserRepository
import com.example.roomdatabase_1.UserViewModel
import com.example.roomdatabase_1.data.AppDatabase
import com.example.roomdatabase_1.data.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

   single {
       Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "my_database")
           .fallbackToDestructiveMigration()
           .build()
   }

     single<UserDao> {get<AppDatabase>().userDao()}

    single {UserRepository(get())}

    viewModel{UserViewModel(get())}


}