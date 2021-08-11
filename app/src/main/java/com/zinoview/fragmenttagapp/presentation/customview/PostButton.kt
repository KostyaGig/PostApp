package com.zinoview.fragmenttagapp.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.zinoview.fragmenttagapp.core.Abstract

/**
 * @author Zinoview on 10.08.2021
 * k.gig@list.ru
 */

interface Button : ViewMapper<Context> {

    fun changeVisible(visibility: Int)
}

class PostButton(
    context: Context, attributes: AttributeSet?
) : androidx.appcompat.widget.AppCompatButton(context,attributes), Button  {

    override fun map(postId: Int, userId: Int, body: String, title: String): Context = context

    override fun changeVisible(visibility: Int) = setVisibility(visibility)
}