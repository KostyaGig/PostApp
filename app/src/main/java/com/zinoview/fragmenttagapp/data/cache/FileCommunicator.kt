package com.zinoview.fragmenttagapp.data.cache

import android.util.Log
import java.io.FileNotFoundException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface FileCommunicator : Record<String>, Read<String> {

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
    }
}