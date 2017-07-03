package com.retrox.jcy.unsplash

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by jichenyang on 03/07/2017.
 */
class UserProfileFragment : LifecycleFragment(){
    var viewModel : UserProfileViewModel? = null
    companion object {
        val UID_KEY = "uid"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userid = arguments.getString(UID_KEY)
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        viewModel?.userId = userid
        viewModel?.user?.observe(this, Observer {
            _ -> //do something
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}