package com.wengelef.kotlinmvvmtest.advanced

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wengelef.kotlinmvvmtest.BR
import com.wengelef.kotlinmvvmtest.R
import com.wengelef.kotlinmvvmtest.model.User
import rx.subjects.PublishSubject

class UsersRecyclerAdapter(private val users: List<User>, private val userClicks: PublishSubject<User>) : RecyclerView.Adapter<UsersRecyclerAdapter.UserItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val viewDatabinding: ViewDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user, parent, false)
        return UserItemViewHolder(viewDatabinding)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(BR.user, UserItemViewModel(users[position], userClicks))
    }

    override fun getItemCount(): Int = users.size

    class UserItemViewHolder(val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root)
}