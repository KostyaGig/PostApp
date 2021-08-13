package com.zinoview.fragmenttagapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.zinoview.fragmenttagapp.core.PostApp

/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */

abstract class BaseFragment : Fragment() {

    @LayoutRes abstract fun layout() : Int

    protected val application by lazy {
        (requireActivity().application as PostApp)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout(),container,false)

    protected companion object {
        const val UI_POST_EXTRA = "UiPost"
    }
}
