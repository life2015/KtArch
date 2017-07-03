package com.retrox.jcy.unsplash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import java.util.concurrent.Executor

/**
 * Created by jichenyang on 03/07/2017.
 */
class UserRepository(val userDao: UserDao, val executor: Executor) {
    var webservice = WebService()


    fun getUser(userId: String): LiveData<User> {
        refreshUser(userId)
        return userDao.load(userId)
    }

    private fun refreshUser(userId: String) {
        executor.execute {

            var userExists = {
                val user = userDao.load(userId)
                user.value != null
            }

            if (userExists.invoke()) {
                webservice.getUser {
                    userDao.insert(it)
                }
            }
        }
    }
}

class WebService {
    fun getUser(block: (User) -> Unit) = User()
}

class UserCache : HashMap<String, LiveData<User>?>()