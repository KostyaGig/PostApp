package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract
import java.io.IOException


/**
 * @author Zinoview on 02.08.2021
 * k.gig@list.ru
 */
class PostUiCacheMapper(
    private val ioExceptionMapper: Abstract.FactoryMapper<IOException, String>
) : Abstract.PostCacheMapper<CachePostUi,IOException> {

    override fun map(data: String): CachePostUi
        = CachePostUi.Success(data)

    override fun map(e: IOException): CachePostUi
        = CachePostUi.Fail(ioExceptionMapper.map(e))
}