package com.zinoview.fragmenttagapp.presentation.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * @author Zinoview on 31.07.2021
 * k.gig@list.ru
 */
class PostTextView(
    context: Context,attributes: AttributeSet?
) : AppCompatTextView(context,attributes),
    ViewMapper<Unit> {

    override fun map(postId: Int,userId: Int,body: String, title: String) = setText(
        giveInfoPost(postId, userId, body, title)
    )

    private fun giveInfoPost(postId: Int,userId: Int,body: String, title: String) : String
        = "Post with id: $postId,\n\n size: $userId, \n\n title: $title ,\n\n body: $body"
}