package com.zinoview.fragmenttagapp.core

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState

/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 * Bad class
 */

interface Check<T : Any?> {

    fun check(arg: T) : Boolean

    class ExistingCacheCheck: Check<Triple<String,String,com.zinoview.fragmenttagapp.presentation.customview.Button>> {

        override fun check(arg: Triple<String,String,com.zinoview.fragmenttagapp.presentation.customview.Button>): Boolean {
            val clickedUiPostText = arg.first
            val currentCache = arg.second
            val deleteCacheBtn = arg.third

            if (isNotEmptyCacheCheck(currentCache)) {
                if (checkContainsCache(clickedUiPostText,currentCache)) {
                    deleteCacheBtn.changeVisible(View.VISIBLE)
                } else {
                    deleteCacheBtn.changeVisible(View.GONE)
                }
            } else {
                deleteCacheBtn.changeVisible(View.GONE)
            }
            return false
        }

        private fun isNotEmptyCacheCheck(src: String) : Boolean  = src.isNotEmpty()

        private fun checkContainsCache(clickedUiPostText: String, cacheUiPostText: String): Boolean
            = cacheUiPostText.contains(clickedUiPostText)
    }

    class RecordStateCheck : Check<Pair<RecordCacheState,com.zinoview.fragmenttagapp.presentation.customview.Button>> {
        override fun check(arg: Pair<RecordCacheState, com.zinoview.fragmenttagapp.presentation.customview.Button>): Boolean {

            val currentRecordCacheState = arg.first
            val deleteCacheButton = arg.second

            when (arg.first) {
                is RecordCacheState.Success -> {
                    showMessage(currentRecordCacheState,deleteCacheButton)
                    deleteCacheButton.changeVisible(View.VISIBLE)
                }
                is RecordCacheState.UpdateSuccess -> {
                    showMessage(currentRecordCacheState,deleteCacheButton)
                    deleteCacheButton.changeVisible(View.GONE)
                }
                is RecordCacheState.Fail -> {
                    showMessage(currentRecordCacheState,deleteCacheButton)
                    deleteCacheButton.changeVisible(View.GONE)
                }
            }
            return false
        }

        private fun showMessage(recordCacheState: RecordCacheState,button: com.zinoview.fragmenttagapp.presentation.customview.Button)
            = recordCacheState.map(button)

    }

    class CreatedCacheFileCheck(
        private val resource: Resource
    ) : Check<Pair<String, com.zinoview.fragmenttagapp.presentation.customview.Button>> {

        override fun check(arg: Pair<String, com.zinoview.fragmenttagapp.presentation.customview.Button>): Boolean {
            val cacheReadText = arg.first

            val context = arg.second.map(-1,-1,"","")

            if (sameText(cacheReadText)) {
                Toast.makeText(context, resource.string(R.string.cache_first_post_text),Toast.LENGTH_SHORT).show()
            }

            return false
        }

        private fun sameText(text: String) : Boolean
            = text == resource.string(R.string.not_created_file_message)
    }

    class SameContentCheck : Check<Pair<String,String>> {

        override fun check(arg: Pair<String, String>): Boolean
            = arg.first.contains(arg.second)
    }
}