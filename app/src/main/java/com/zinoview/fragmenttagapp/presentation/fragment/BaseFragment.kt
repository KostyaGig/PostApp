package com.zinoview.fragmenttagapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */
abstract class BaseFragment : Fragment() {

    @LayoutRes protected abstract fun layout() : Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        printMessageToConsole("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            printMessageToConsole("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        printMessageToConsole("onCreateView")
        return inflater.inflate(layout(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printMessageToConsole("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        printMessageToConsole("onStart")
    }

    override fun onResume() {
        super.onResume()
        printMessageToConsole("onResume")
    }

    override fun onPause() {
        super.onPause()
        printMessageToConsole("onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        printMessageToConsole("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        printMessageToConsole("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        printMessageToConsole("onDetach")
    }

    protected companion object {
        const val UI_POST_EXTRA = "UiPost"
    }
}

fun Fragment.printMessageToConsole(message: String) {
    Log.d("TestFragment","${this.javaClass}, $message")
}