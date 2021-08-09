package com.zinoview.fragmenttagapp.presentation.cache

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface CachePostCommunication<T : Any> {

    //todo create shared (общий) interface or abstract class,maybe abstract class with interface and move shared logic to here
    fun observe(owner: LifecycleOwner, observer: Observer<T>)

    fun changeValue(value: T)

    abstract class Base<T: Any> : CachePostCommunication<T> {
        private val mutablePostLiveData = MutableLiveData<T>()
        private val postLiveData: LiveData<T> = mutablePostLiveData

        override fun observe(owner: LifecycleOwner, observer: Observer<T>)
            = postLiveData.observe(owner, observer)

        override fun changeValue(@NonNull value: T) {
            mutablePostLiveData.value = value
        }

        //todo rename indpendent context
        class CacheRecordPostCommunication : Base<RecordCacheState>()
        class CacheReadPostCommunication : Base<String>()
    }
}