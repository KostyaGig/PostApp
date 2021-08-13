package com.zinoview.fragmenttagapp.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.zinoview.fragmenttagapp.presentation.Communication

/**
 * @author Zinoview on 12.08.2021
 * k.gig@list.ru
 */
interface FragmentManager {

    fun provide() : FragmentManager

    fun changeTitle(communication: Communication<String>, title: String)

    class Base(
        private val activity: AppCompatActivity
    ) : com.zinoview.fragmenttagapp.core.FragmentManager {

        override fun provide(): FragmentManager
            = activity.supportFragmentManager

        override fun changeTitle(communication: Communication<String>, title: String)
            = communication.changeValue(title)
    }
}