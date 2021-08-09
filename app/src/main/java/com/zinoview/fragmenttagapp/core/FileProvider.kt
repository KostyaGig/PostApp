package com.zinoview.fragmenttagapp.core

import android.content.Context
import android.util.Log
import java.io.File

/**
 * @author Zinoview on 02.08.2021
 * k.gig@list.ru
 */

interface FileProvider {

    fun file() : File

    class Base(
        private val context: Context
    ) : FileProvider {

        override fun file(): File {
            val rootFile = context.filesDir
            val postDirectory = postDirectory(rootFile)

            return postFile(postDirectory)
        }


        private fun postDirectory(rootFile: File) : File {
            val postDirectory = File(rootFile,POST_DIRECTORY)
            postDirectory.mkdirs()
            return postDirectory
        }

        private fun postFile(postDirectory: File) : File {
            val file = File(postDirectory, CACHE_FILE)
            file.appendText("")
            return file
        }


        private companion object {
            const val POST_DIRECTORY = "PostCache"
            const val CACHE_FILE = "Post.txt"
        }
    }

}