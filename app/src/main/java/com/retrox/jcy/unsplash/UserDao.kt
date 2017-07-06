package com.retrox.jcy.unsplash

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by jichenyang on 03/07/2017.
 */
@Dao
interface UserDao {

    @Query("select * from user where id = :userId")
    fun load(userId: String): LiveData<User>

    @Query("Select * from user")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)//或者其他的插入策略
    fun insert(vararg users: User)

    @Delete
    fun delete(user: User)

}