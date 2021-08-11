package com.zinoview.fragmenttagapp.presentation.cache

import android.content.Context
import android.widget.Toast
import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 09.08.2021
 * k.gig@list.ru
 * Class for [com.zinoview.fragmenttagapp.presentation.cache.CachePostCommunication.Base.CacheRecordPostCommunication]
 */
sealed class RecordCacheState : Abstract.FactoryMapper<com.zinoview.fragmenttagapp.presentation.customview.Button,Unit> {

    protected lateinit var context: Context

    override fun map(src: com.zinoview.fragmenttagapp.presentation.customview.Button) {
        context = src.map(-1,-1,"","")
    }

    open fun showMessage(message: String) = Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

    class Success(
        private val message: String
    ) : RecordCacheState() {

        override fun map(src: com.zinoview.fragmenttagapp.presentation.customview.Button) {
            super.map(src)
            showMessage(message)
        }
    }

    class UpdateSuccess(
        private val message: String
    ) : RecordCacheState() {

        override fun map(src: com.zinoview.fragmenttagapp.presentation.customview.Button) {
            super.map(src)
            showMessage(message)
        }

    }

    class Fail(
        private val message: String
    ) : RecordCacheState() {

        override fun map(src: com.zinoview.fragmenttagapp.presentation.customview.Button) {
            super.map(src)
            showMessage(message)
        }

    }

}
