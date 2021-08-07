package com.zinoview.fragmenttagapp.core

import android.content.Context
import androidx.annotation.StringRes


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
interface Resource {

    fun string(@StringRes id: Int) : String

    class Base(
        private val context: Context
    ) : Resource {

        override fun string(id: Int): String = context.getString(id)
    }
}