package com.zinoview.fragmenttagapp.data.cache

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState
import java.io.FileNotFoundException
import java.io.IOException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface CachePostRepository {

    suspend fun writeData(data: String): CacheDataPost<RecordCacheState>
    suspend fun data() : CacheDataPost<String>
    suspend fun commonData() : String
    suspend fun updateFile(newCache: String) : CacheDataPost<RecordCacheState>

    class Base(
        private val fileCommunicator: FileCommunicator,
        private val resource: Resource,
    ) : CachePostRepository {

        override suspend fun writeData(data: String): CacheDataPost<RecordCacheState> {
            return try {
                fileCommunicator.writeData(data)
                CacheDataPost.RecordCacheDataPost.Success(resource.string(R.string.success_writing_data_to_cache_message))
            } catch (e: Exception) {
                CacheDataPost.RecordCacheDataPost.Fail(e)
            }
        }

        override suspend fun data() : CacheDataPost<String> {
            return try {
                CacheDataPost.ReadCacheDataPost.Success(fileCommunicator.data())
            } catch (e: FileNotFoundException) {
                CacheDataPost.ReadCacheDataPost.Fail(e)
            }
        }

        override suspend fun commonData(): String
            = fileCommunicator.data()

        override suspend fun updateFile(newCache: String): CacheDataPost<RecordCacheState> = try {
            fileCommunicator.updateFile(newCache)
            CacheDataPost.RecordCacheDataPost.UpdateSuccess(resource.string(R.string.success_updating_cache_file)) //todo use resource
        } catch (e: IOException) {
            CacheDataPost.RecordCacheDataPost.Fail(FailUpdatedFileException())
        }
    }

}