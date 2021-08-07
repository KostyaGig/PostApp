package com.zinoview.fragmenttagapp.core

import androidx.fragment.app.Fragment
import com.zinoview.fragmenttagapp.presentation.fragment.BaseFragment


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */
interface Check<T : Any?> {

    fun check(arg: T) : Boolean

    class NullCheck : Check<Fragment?> {
        override fun check(arg: Fragment?): Boolean = arg == null
    }
}