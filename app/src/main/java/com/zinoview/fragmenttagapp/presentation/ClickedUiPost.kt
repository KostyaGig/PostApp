package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.customview.ViewMapper
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */


data class ClickedUiPost(
    private val postId: Int,
    private val userId: Int,
    private val body: String,
    private val title: String
) : Serializable, Abstract.Object {

    fun map(mapper: ViewMapper) = mapper.map(postId, this.bytes().size, body, title)

    override fun <T> map(mapper: Abstract.PostMapper<T>): T
        = mapper.map(postId,userId,title,body)
}

fun <T : Serializable> T.bytes() : ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val objectOutPut = ObjectOutputStream(byteArrayOutputStream)

    objectOutPut.use {
        it.writeObject(this)
        return byteArrayOutputStream.toByteArray()
    }
}