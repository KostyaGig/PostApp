package com.zinoview.fragmenttagapp.data.cache

import com.zinoview.fragmenttagapp.core.FileProvider
import java.io.*


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface File<T> {

    fun write(data: T)
    fun data() : T
    fun update(newData: String)

    class TxtFile(
        private val fileProvider: FileProvider,
    ) : File<String> {

        @Throws(Exception::class)
        override fun write(data: String)
            = fileProvider.file().appendText(data)

        @Throws(FileNotFoundException::class)
        override fun data(): String
            = FileInputStream(fileProvider.file()).bufferedReader().use { it.readText() }

        /**
         * @param append - false for rewrite file, true for append
         */
        @Throws(IOException::class)
        override fun update(newData: String) {
            val fos = FileOutputStream(fileProvider.file(),false)

            fos.use {fous ->
                fous.write(newData.toByteArray())
            }
        }
    }
}