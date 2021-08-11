package com.zinoview.fragmenttagapp.service_locator.core

import android.content.Context
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.Check
import com.zinoview.fragmenttagapp.core.FileProvider
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.data.PostRepository
import com.zinoview.fragmenttagapp.data.cache.CachePostRepository
import com.zinoview.fragmenttagapp.data.cache.File
import com.zinoview.fragmenttagapp.data.cache.FileCommunicator
import com.zinoview.fragmenttagapp.data.cloud.PostCloudDataSource
import com.zinoview.fragmenttagapp.data.cloud.PostDataMapper
import com.zinoview.fragmenttagapp.data.cloud.PostService
import com.zinoview.fragmenttagapp.domain.ListPostDomainMapper
import com.zinoview.fragmenttagapp.domain.PostDomain
import com.zinoview.fragmenttagapp.domain.PostDomainMapper
import com.zinoview.fragmenttagapp.domain.PostInteractor
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor
import com.zinoview.fragmenttagapp.domain.cache.PostDomainCacheMapper
import com.zinoview.fragmenttagapp.presentation.*
import com.zinoview.fragmenttagapp.presentation.cache.*
import com.zinoview.fragmenttagapp.presentation.navigation.ModelNavigator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


/**
 * @author Zinoview on 11.08.2021
 * k.gig@list.ru
 */
interface DependencyContainer {

    fun init(context: Context)

    class Core : DependencyContainer {
        lateinit var resource: Resource

        override fun init(context: Context) {
            resource = Resource.Base(context)
        }
    }

    class Network : DependencyContainer {

        lateinit var postCloudDataSource: PostCloudDataSource

        override fun init(context: Context) {

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

            postCloudDataSource = PostCloudDataSource.Base(
                postService
            )
        }

        private companion object {
            const val BASE_URL = "https://jsonplaceholder.typicode.com"
        }
    }

    class Post(
        private val network: Network,
        private val cache: Cache,
        private val core: Core
    ) : DependencyContainer {

        lateinit var postRepository: PostRepository
        lateinit var postInteractor: PostInteractor

        lateinit var cachePostInteractor: CachePostInteractor
        lateinit var listPostUiMapper: Abstract.ListPostMapper<PostDomain, ListPostUi, IOException>
        lateinit var modelNavigator: ModelNavigator
        lateinit var communicate: Pair<Communication<List<UiPost>>,Abstract.PostMapper<UiPost>>

        override fun init(context: Context) {
            postRepository = PostRepository.Base(
                network.postCloudDataSource,
                PostDataMapper()
            )

            postInteractor = PostInteractor.Base(
                postRepository,
                ListPostDomainMapper(
                    PostDomainMapper(),
                    Abstract.FactoryMapper.CloudExceptionMapper()
                )
            )

            cachePostInteractor = cache.cachePostInteractor

            listPostUiMapper = ListPostUiMapper(
                PostUiMapper(),
                Abstract.FactoryMapper.IOExceptionMapper(core.resource)
            )

            modelNavigator = ModelNavigator.Base(Check.NullCheck())

            val postCommunication = PostCommunication()
            communicate = Pair(postCommunication,UiPostMapper())
        }
    }

    class Cache(
        private val core: Core
    ) : DependencyContainer {

        lateinit var fileCommunicator: FileCommunicator
        lateinit var cachePostInteractor: CachePostInteractor

        lateinit var cachePostCommunications: Pair<Communication<RecordCacheState>, Communication<String>>
        lateinit var uiPostCacheMappers: Pair<UiPostCacheMapper<RecordCacheState>,UiPostCacheMapper<String>>

        override fun init(context: Context) {

            val fileProvider = FileProvider.Base(context)

            val txtFile = File.TxtFile(
                fileProvider,
            )

            fileCommunicator = FileCommunicator.Base(txtFile)

            val cachePostRepository = CachePostRepository.Base(
                fileCommunicator,
                core.resource
            )

            val cacheExceptionMapper = Abstract.FactoryMapper.CacheExceptionMapper()

            cachePostInteractor = CachePostInteractor.Base(
                cachePostRepository,
                Pair(
                    PostDomainCacheMapper.Record(
                        cacheExceptionMapper,
                        Abstract.FactoryMapper.RecordDomainCacheStringMapper()
                    ),
                    PostDomainCacheMapper.Read(cacheExceptionMapper)
                )
            )

            cachePostCommunications = Pair(
                CacheRecordPostCommunication(),
                CacheReadPostCommunication()
            )

            val ioExceptionMapper = Abstract.FactoryMapper.CacheErrorMessageMapper(core.resource)

            uiPostCacheMappers = Pair(
                UiPostCacheMapper.Record(
                    ioExceptionMapper,
                    Abstract.FactoryMapper.RecordUiCacheStringMapper()
                ),
                UiPostCacheMapper.Read(ioExceptionMapper)
            )
        }
    }

    class Checker(
        private val core: Core,
        private val cache: Cache
    ) : DependencyContainer {

        lateinit var existingCacheCheck:
                Check<Triple<String, String, com.zinoview.fragmenttagapp.presentation.customview.Button>>

        lateinit var recordCacheStateCheck:
                Check<Pair<RecordCacheState, com.zinoview.fragmenttagapp.presentation.customview.Button>>

        lateinit var createdCacheFileCheck:
                Check<Pair<String, com.zinoview.fragmenttagapp.presentation.customview.Button>>

        lateinit var sameContentCheck:
                Check<Pair<String, String>>

        lateinit var cachePostInteractor: CachePostInteractor

        lateinit var resource: Resource

        override fun init(context: Context) {
            resource = core.resource

            existingCacheCheck = Check.ExistingCacheCheck()
            recordCacheStateCheck = Check.RecordStateCheck()
            createdCacheFileCheck = Check.CreatedCacheFileCheck(resource)
            sameContentCheck = Check.SameContentCheck()

            cachePostInteractor = cache.cachePostInteractor
        }

    }

}