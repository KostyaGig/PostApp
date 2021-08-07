package com.zinoview.fragmenttagapp.data.cache

import com.zinoview.fragmenttagapp.core.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface File<T> {

    fun write(data: T)
    fun data() : T

    class TxtFile(
        private val fileProvider: FileProvider,
    ) : com.zinoview.fragmenttagapp.data.cache.File<String> {

        @Throws(Exception::class)
        override fun write(data: String)
            = fileProvider.file().appendText(data)

        @Throws(FileNotFoundException::class)
        override fun data(): String
            = FileInputStream(fileProvider.file()).bufferedReader().use { it.readText() }
    }
}