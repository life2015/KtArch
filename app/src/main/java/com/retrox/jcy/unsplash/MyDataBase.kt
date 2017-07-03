package com.retrox.jcy.unsplash

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import kotlin.reflect.KClass

/**
 * Created by jichenyang on 03/07/2017.
 */
@Database(entities = arrayOf(User::class) , version = 1)
abstract class MyDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}