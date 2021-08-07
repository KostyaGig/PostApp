package com.zinoview.fragmenttagapp.data

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.data.cache.FileCommunicator
import java.io.FileNotFoundException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface CachePostRepository {

    suspend fun writeData(data: String): CacheDataPost
    suspend fun data() : CacheDataPost

    class Base(
        private val fileCommunicator: FileCommunicator,
        private val resource: Resource
    ) : CachePostRepository {

        override suspend fun writeData(data: String): CacheDataPost {
            return try {
                fileCommunicator.writeData(data)
                CacheDataPost.Success(resource.string(R.string.success_writing_data_to_cache_message))
            } catch (e: Exception) {
                CacheDataPost.Fail(e)
            }
        }

        override suspend fun data() : CacheDataPost {
            return try {
                CacheDataPost.Success(fileCommunicator.data())
            } catch (e: FileNotFoundException) {
                CacheDataPost.Fail(e)
            }
        }
    }
}