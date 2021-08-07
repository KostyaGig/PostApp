package com.zinoview.fragmenttagapp.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface CachePostCommunication {

    //todo create shared (общий) interface or abstract class,maybe abstract class with interface and move shared logic to here
    fun observe(owner: LifecycleOwner, observer: Observer<String>)

    fun changeValue(value: String)

    class Base : CachePostCommunication {
        private val mutablePostLiveData = MutableLiveData<String>()
        private val postLiveData: LiveData<String> = mutablePostLiveData

        override fun observe(owner: LifecycleOwner, observer: Observer<String>)
            = postLiveData.observe(owner, observer)

        override fun changeValue(value: String) {
            mutablePostLiveData.value = value
        }
    }
}