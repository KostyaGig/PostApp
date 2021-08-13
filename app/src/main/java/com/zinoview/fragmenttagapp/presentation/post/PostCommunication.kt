package com.zinoview.fragmenttagapp.presentation.post

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zinoview.fragmenttagapp.presentation.Communication


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostCommunication : Communication.Base<List<UiPost>>() {
    private val mutablePostLiveData = MutableLiveData<List<UiPost>>()
    private val postLiveData: LiveData<List<UiPost>> = mutablePostLiveData

    override fun observe(owner: LifecycleOwner, observer: Observer<List<UiPost>>)
        = postLiveData.observe(owner, observer)


    override fun changeValue(value: List<UiPost>) {
        mutablePostLiveData.value = value
    }
}