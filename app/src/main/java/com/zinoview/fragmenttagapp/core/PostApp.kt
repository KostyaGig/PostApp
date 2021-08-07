package com.zinoview.fragmenttagapp.core

import android.app.Application
import com.zinoview.fragmenttagapp.data.CachePostRepository
import com.zinoview.fragmenttagapp.data.PostRepository
import com.zinoview.fragmenttagapp.data.cache.File
import com.zinoview.fragmenttagapp.data.cache.FileCommunicator
import com.zinoview.fragmenttagapp.data.cloud.PostCloudDataSource
import com.zinoview.fragmenttagapp.data.cloud.PostDataMapper
import com.zinoview.fragmenttagapp.data.cloud.PostService
import com.zinoview.fragmenttagapp.domain.CachePostInteractor
import com.zinoview.fragmenttagapp.domain.ListPostDomainMapper
import com.zinoview.fragmenttagapp.domain.PostDomainMapper
import com.zinoview.fragmenttagapp.domain.PostInteractor
import com.zinoview.fragmenttagapp.presentation.*
import com.zinoview.fragmenttagapp.presentation.navigation.ModelNavigator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostApp : Application() {

    lateinit var postViewModel: PostViewModel
    lateinit var cachePostViewModel: CachePostViewModel

    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postService = retrofit.create(PostService::class.java)
        val postCloudDataSource = PostCloudDataSource.Base(postService)
        val postRepository = PostRepository.Base(
            postCloudDataSource,
            PostDataMapper()
        )

        val postInteractor = PostInteractor.Base(
            postRepository,
            ListPostDomainMapper(PostDomainMapper(),Abstract.FactoryMapper.ExceptionMapper())
        )
        val postCommunication = PostCommunication.Base()

        val resource = Resource.Base(this)
        postViewModel = PostViewModel(
            postInteractor,
            ListPostUiMapper(PostUiMapper(),Abstract.FactoryMapper.IOExceptionMapper(resource)),
            ModelNavigator.Base(Check.NullCheck()),
            Pair(postCommunication,UiPostMapper())
        )

        val fileProvider = FileProvider.Base(this)

        val txtFile = File.TxtFile(
            fileProvider,
        )

        val fileComunicator = FileCommunicator.Base(txtFile)

        val cachePostRepository = CachePostRepository.Base(fileComunicator)
        val cachePostInteractor = CachePostInteractor.Base(
            cachePostRepository
        )

        val ioExceptionMapper = Abstract.FactoryMapper.CacheErrorMessageMapper(resource)
        cachePostViewModel = CachePostViewModel(
            cachePostInteractor,
            CachePostCommunication.Base(),
            PostUiCacheMapper(ioExceptionMapper)
        )
    }

    private companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}