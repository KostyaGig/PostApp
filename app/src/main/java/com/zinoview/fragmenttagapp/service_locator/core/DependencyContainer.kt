package com.zinoview.fragmenttagapp.service_locator.core

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.Check
import com.zinoview.fragmenttagapp.core.FileProvider
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.data.cache.CacheExceptionMapper
import com.zinoview.fragmenttagapp.data.cache.CachePostRepository
import com.zinoview.fragmenttagapp.data.cache.File
import com.zinoview.fragmenttagapp.data.cache.FileCommunicator
import com.zinoview.fragmenttagapp.data.cloud.*
import com.zinoview.fragmenttagapp.domain.*
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor
import com.zinoview.fragmenttagapp.domain.cache.PostDomainCacheMapper
import com.zinoview.fragmenttagapp.domain.cache.RecordDomainCacheStringMapper
import com.zinoview.fragmenttagapp.presentation.*
import com.zinoview.fragmenttagapp.presentation.cache.*
import com.zinoview.fragmenttagapp.presentation.navigation.Navigator
import com.zinoview.fragmenttagapp.presentation.navigation.TitleToolbarCommuncation
import com.zinoview.fragmenttagapp.presentation.navigation.TypeFragmentNavigator
import com.zinoview.fragmenttagapp.presentation.post.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.Serializable


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

        lateinit var postRepository: PostCloudRepository
        lateinit var postInteractor: PostInteractor

        lateinit var cachePostInteractor: CachePostInteractor
        lateinit var listPostUiMapper: Abstract.ListPostMapper<PostDomain, ListPostUi, IOException>
        lateinit var communicate: Pair<Communication<List<UiPost>>,Abstract.PostMapper<UiPost>>

        override fun init(context: Context) {
            postRepository = PostCloudRepository.Base(
                network.postCloudDataSource,
                PostDataMapper()
            )

            postInteractor = PostInteractor.Base(
                postRepository,
                ListPostDomainMapper(
                    PostDomainMapper(),
                    CloudExceptionMapper()
                )
            )

            cachePostInteractor = cache.cachePostInteractor

            listPostUiMapper = ListPostUiMapper(
                PostUiMapper(),
                IOExceptionMapper(core.resource)
            )

            val postCommunication = PostCommunication()
            communicate = Pair(postCommunication, UiPostMapper())

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

            val cacheExceptionMapper = CacheExceptionMapper()

            cachePostInteractor = CachePostInteractor.Base(
                cachePostRepository,
                Pair(
                    PostDomainCacheMapper.Record(
                        cacheExceptionMapper,
                        RecordDomainCacheStringMapper(core.resource)
                    ),
                    PostDomainCacheMapper.Read(cacheExceptionMapper)
                )
            )

            cachePostCommunications = Pair(
                CacheRecordPostCommunication(),
                CacheReadPostCommunication()
            )

            val ioExceptionMapper = CacheErrorMessageMapper(core.resource)

            uiPostCacheMappers = Pair(
                UiPostCacheMapper.Record(
                    ioExceptionMapper,
                    RecordUiCacheStringMapper(core.resource)
                ),
                UiPostCacheMapper.Read(ioExceptionMapper)
            )
        }
    }

    class Navigation(
        private val core: Core
    ) : DependencyContainer {
        lateinit var navigator: Navigator
        lateinit var communication: Communication<String>

        override fun init(context: Context) {

            communication = TitleToolbarCommuncation()
            navigator = Navigator.Base(communication,core.resource)
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

    class Mapper : DependencyContainer {

        lateinit var postMapper: Abstract.PostMapper<String>
        lateinit var menuItemIdMapper: Abstract.FactoryMapper<MenuItem,TypeFragmentNavigator>
        lateinit var recyclerViewTypeMapper: Abstract.FactoryMapper<UiPost, Int>

        override fun init(context: Context) {
            postMapper = PostTextMapper()
            menuItemIdMapper = MenuItemIdMapper()
            recyclerViewTypeMapper = RecyclerViewTypeMapper()
        }
    }

    class Generator : DependencyContainer {

        lateinit var bundleGenerator
            : com.zinoview.fragmenttagapp.presentation.Generator<Pair<String, Serializable>, Bundle>

        override fun init(context: Context) {
            bundleGenerator = com.zinoview.fragmenttagapp.presentation.Generator.Bundle()
        }

    }
}