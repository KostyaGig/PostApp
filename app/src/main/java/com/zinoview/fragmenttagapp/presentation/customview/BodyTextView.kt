package com.zinoview.fragmenttagapp.presentation.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 31.07.2021
 * k.gig@list.ru
 */
class BodyTextView(
    context: Context,attributes: AttributeSet?
) : AppCompatTextView(context,attributes),
    ViewMapper {

    override fun map(postId: Int,userId: Int,body: String, title: String) = setText(body)

}