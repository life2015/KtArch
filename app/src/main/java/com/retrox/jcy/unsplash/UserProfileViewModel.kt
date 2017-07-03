package com.retrox.jcy.unsplash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jichenyang on 03/07/2017.
 */
class UserProfileViewModel(val userRepo: UserRepository) : ViewModel() {
    var userId = ""
    var user: LiveData<User>? = null

    fun init(userId: String?) {
        userId?.apply {
            user = userRepo.getUser(userId)
        }
    }
}

@Poko
@Entity(tableName = "user")
data class User(@PrimaryKey var id: Int, var name: String, var lastName: String) {
    constructor() : this(0,"","")
}