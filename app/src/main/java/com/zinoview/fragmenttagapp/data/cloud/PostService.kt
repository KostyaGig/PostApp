package com.zinoview.fragmenttagapp.data.cloud

import retrofit2.http.GET


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 *
 * BASE_URL: "https://jsonplaceholder.typicode.com"
 */
interface PostService {

    @GET("/posts")
    suspend fun posts() : List<PostCloud>
}