package com.zinoview.fragmenttagapp.data.cache

import android.util.Log
import java.io.FileNotFoundException
import java.io.IOException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface FileCommunicator : Record<String>, Read<String>, Clean<Unit> {

    class Base(
        private val file: File<String>,
    ): FileCommunicator {

        @Throws(Exception::class)
        override fun writeData(data: String) {
            file.write(data)
        }

        @Throws(FileNotFoundException::class)
        override fun data(): String
            = file.data()

        @Throws(IOException::class)
        override fun updateFile(newData: String)
            = file.update(newData)
    }

}