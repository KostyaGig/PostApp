package com.zinoview.fragmenttagapp.core

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 * Bad class
 */
@Deprecated("Use Matches interface")
interface Check<T : Any?> {

    fun check(arg: T) : Boolean

    class NullCheck : Check<Fragment?> {
        override fun check(arg: Fragment?): Boolean = arg == null
    }

    class ExistingCacheCheck: Check<Triple<String,String,Button>> {
        override fun check(arg: Triple<String,String, Button>): Boolean {
            val clickedUiPostText = arg.first
            val cacheUiPostText = arg.second
            if (isNotEmptyCacheCheck(cacheUiPostText)) {
                if (checkContainsCache(clickedUiPostText,cacheUiPostText)) {
                    arg.third.visibility = View.VISIBLE
                } else {
                    arg.third.visibility = View.GONE
                }
            } else {
                arg.third.visibility = View.GONE
            }
            return false
        }

        private fun isNotEmptyCacheCheck(src: String) : Boolean  = src.isNotEmpty()

        private fun checkContainsCache(clickedUiPostText: String, cacheUiPostText: String): Boolean
            = cacheUiPostText.contains(clickedUiPostText)
    }

    class SameContentCheck : Check<Pair<String,String>> {

        override fun check(arg: Pair<String, String>): Boolean
            = arg.first.contains(arg.second)
    }

    class RecordStateCheck : Check<Pair<RecordCacheState,Button>> {

        override fun check(arg: Pair<RecordCacheState, Button>): Boolean {

            val currentRecordCacheState = arg.first
            val deleteCacheButton = arg.second

            when (arg.first) {
                is RecordCacheState.Success -> {
                    showMessage(currentRecordCacheState,deleteCacheButton)
                    showButton(View.VISIBLE,deleteCacheButton)
                }
                is RecordCacheState.UpdateSuccess -> {
                    showMessage(currentRecordCacheState,deleteCacheButton)
                    showButton(View.GONE,deleteCacheButton)
                }
                is RecordCacheState.Fail -> {
                    showMessage(currentRecordCacheState,deleteCacheButton)
                    showButton(View.GONE,deleteCacheButton)
                }
            }
            return false
        }

        private fun showMessage(recordCacheState: RecordCacheState,button: Button)
            = recordCacheState.map(button)

        private fun showButton(visibility: Int,button: Button) {
            button.visibility = visibility
        }
    }

    class CreatedCacheFileCheck : Check<Pair<String,Button>> {

        override fun check(arg: Pair<String, Button>): Boolean {
            val cacheReadText = arg.first
            //use button for getting context
            val context = arg.second.context

            if (sameText(cacheReadText)) {
                Toast.makeText(context, CACHE_POST_TEXT,Toast.LENGTH_SHORT).show()
            }

            return false
        }

        private fun sameText(text: String) : Boolean
            = text == FILE_NOT_CREATED_TEXT // todo use resource

        private companion object {
            const val FILE_NOT_CREATED_TEXT = "File yet not created"
            const val CACHE_POST_TEXT = "Please cache you first post!"
        }
    }
}