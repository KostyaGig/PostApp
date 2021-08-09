package com.zinoview.fragmenttagapp.presentation.cache

import android.content.Context
import android.widget.Button
import android.widget.Toast
import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 09.08.2021
 * k.gig@list.ru
 * Class for [com.zinoview.fragmenttagapp.presentation.cache.CachePostCommunication.Base.CacheRecordPostCommunication]
 */
sealed class RecordCacheState : Abstract.FactoryMapper<Button,Unit> {

    class Success(
        private val message: String
    ) : RecordCacheState() {

        override fun map(src: Button)
            = Toast.makeText(src.context,message,Toast.LENGTH_SHORT).show()

    }

    class UpdateSuccess(
        private val message: String
    ) : RecordCacheState() {

        override fun map(src: Button)
            = Toast.makeText(src.context,message,Toast.LENGTH_SHORT).show()
    }

    class Fail(
        private val message: String
    ) : RecordCacheState() {

        override fun map(src: Button)
            = Toast.makeText(src.context,message,Toast.LENGTH_SHORT).show()
    }
}
