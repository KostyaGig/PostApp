package com.zinoview.fragmenttagapp.presentation.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar


/**
 * @author Zinoview on 12.08.2021
 * k.gig@list.ru
 */
class PostToolbar(
    context: Context, attributes: AttributeSet?
) : Toolbar(context,attributes),
    ViewMapper<Unit> {

    override fun map(postId: Int, userId: Int, body: String, title: String) = setTitle(title)
}