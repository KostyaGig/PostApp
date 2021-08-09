package com.zinoview.fragmenttagapp.core

import android.app.Application
import com.zinoview.fragmenttagapp.data.cache.CachePostRepository
import com.zinoview.fragmenttagapp.data.PostRepository
import com.zinoview.fragmenttagapp.data.cache.File
import com.zinoview.fragmenttagapp.data.cache.FileCommunicator
import com.zinoview.fragmenttagapp.data.cloud.PostCloudDataSource
import com.zinoview.fragmenttagapp.data.cloud.PostDataMapper
import com.zinoview.fragmenttagapp.data.cloud.PostService
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor
import com.zinoview.fragmenttagapp.domain.ListPostDomainMapper
import com.zinoview.fragmenttagapp.domain.PostDomainMapper
import com.zinoview.fragmenttagapp.domain.PostInteractor
import com.zinoview.fragmenttagapp.domain.cache.PostReadDomainCacheMapper
import com.zinoview.fragmenttagapp.domain.cache.PostRecordDomainCacheMapper
import com.zinoview.fragmenttagapp.presentation.*
import com.zinoview.fragmenttagapp.presentation.cache.CachePostCommunication
import com.zinoview.fragmenttagapp.presentation.cache.CachePostViewModel
import com.zinoview.fragmenttagapp.presentation.cache.UiPostCacheMapper
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
    lateinit var fileComunicator: FileCommunicator

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
            ListPostDomainMapper(PostDomainMapper(),Abstract.FactoryMapper.CloudExceptionMapper())
        )
        val resource = Resource.Base(this)

        val fileProvider = FileProvider.Base(this)

        val txtFile = File.TxtFile(
            fileProvider,
        )

        fileComunicator = FileCommunicator.Base(txtFile)

        val cachePostRepository = CachePostRepository.Base(fileComunicator,resource)
        val cachePostInteractor = CachePostInteractor.Base(
            cachePostRepository,
            PostRecordDomainCacheMapper(Abstract.FactoryMapper.CacheExceptionMapper(),Abstract.FactoryMapper.RecordDomainCacheStringMapper()),
            PostReadDomainCacheMapper(Abstract.FactoryMapper.CacheExceptionMapper()),
        )

        val ioExceptionMapper = Abstract.FactoryMapper.CacheErrorMessageMapper(resource)
        cachePostViewModel = CachePostViewModel(
            cachePostInteractor,
            Pair(
                CachePostCommunication.Base.CacheRecordPostCommunication(),
                CachePostCommunication.Base.CacheReadPostCommunication()),
            Pair(
                UiPostCacheMapper.Record(ioExceptionMapper,Abstract.FactoryMapper.RecordUiCacheStringMapper()),
                UiPostCacheMapper.Read(ioExceptionMapper)
            )
        )

        val postCommunication = PostCommunication.Base()
        postViewModel = PostViewModel(
            postInteractor,
            cachePostInteractor,
            ListPostUiMapper(PostUiMapper(),Abstract.FactoryMapper.IOExceptionMapper(resource)),
            ModelNavigator.Base(Check.NullCheck()),
            Pair(postCommunication,UiPostMapper())
        )
    }

    private companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
}