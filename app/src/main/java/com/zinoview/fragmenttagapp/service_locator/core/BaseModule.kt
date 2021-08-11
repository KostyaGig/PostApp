package com.zinoview.fragmenttagapp.service_locator.core

import androidx.lifecycle.ViewModel


/**
 * @author Zinoview on 10.08.2021
 * k.gig@list.ru
 */
interface BaseModule<T : ViewModel> {

    fun getViewModel() : T
}