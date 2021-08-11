package com.zinoview.fragmenttagapp.presentation

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * @author Zinoview on 10.08.2021
 * k.gig@list.ru
 */
interface Communication<T> {

    fun observe(owner: LifecycleOwner, observer: Observer<T>)

    fun changeValue(value: T)

    abstract class Base<T: Any> : Communication<T> {
        private val mutablePostLiveData = MutableLiveData<T>()
        private val postLiveData: LiveData<T> = mutablePostLiveData

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
            postLiveData.observe(owner, observer)

        override fun changeValue(@NonNull value: T) {
            mutablePostLiveData.value = value
        }
    }
}