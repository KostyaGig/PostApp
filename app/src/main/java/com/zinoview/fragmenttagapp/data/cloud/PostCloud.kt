package com.zinoview.fragmenttagapp.data.cloud

import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostCloud(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("userId")
    private val userId: Int,
    @SerializedName("title")
    private val title: String,
    @SerializedName("body")
    private val body: String
) : Abstract.Object {

    override fun <T> map(mapper: Abstract.PostMapper<T>): T = mapper.map(id, userId, title, body)
}