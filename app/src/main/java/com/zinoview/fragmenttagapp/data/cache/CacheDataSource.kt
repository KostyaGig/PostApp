package com.zinoview.fragmenttagapp.data.cache

import com.zinoview.fragmenttagapp.data.DataSource
import java.io.FileNotFoundException
import kotlin.jvm.Throws


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface CacheDataSource : DataSource<String>, Record<String> {

    class Base(
        private val file: FileCommunicator
    ) : CacheDataSource {

        @Throws(Exception::class)
        override fun writeData(data: String)
            = file.writeData(data)

        @Throws(FileNotFoundException::class)
        override suspend fun data(): String
            = file.data()
    }

}