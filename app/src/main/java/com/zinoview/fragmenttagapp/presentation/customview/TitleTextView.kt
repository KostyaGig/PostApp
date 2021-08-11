package com.zinoview.fragmenttagapp.presentation.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * @author Zinoview on 31.07.2021
 * k.gig@list.ru
 */
class TitleTextView(
    context: Context,attributes: AttributeSet?
) : AppCompatTextView(context,attributes),
    ViewMapper<Unit> {

    override fun map(postId: Int,userId: Int,body: String, title: String) = setText(title)
}