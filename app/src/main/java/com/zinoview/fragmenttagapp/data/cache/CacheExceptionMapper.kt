package com.zinoview.fragmenttagapp.data.cache

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.domain.cache.FileNotCreatedException
import com.zinoview.fragmenttagapp.domain.cache.GenericFileException
import java.io.FileNotFoundException
import java.io.IOException


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */
class CacheExceptionMapper : Abstract.FactoryMapper<Exception, IOException> {
    override fun map(src: Exception): IOException = when(src) {
        is FileNotFoundException -> FileNotCreatedException()
        is FailUpdatedFileException -> com.zinoview.fragmenttagapp.domain.cache.FailUpdatedFileException()
        else -> GenericFileException()
    }
}