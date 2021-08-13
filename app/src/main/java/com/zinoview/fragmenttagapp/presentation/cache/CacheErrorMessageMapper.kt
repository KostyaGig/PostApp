package com.zinoview.fragmenttagapp.presentation.cache

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.domain.cache.FileNotCreatedException
import java.io.IOException


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */

class CacheErrorMessageMapper(
    private val resource: Resource
) : Abstract.FactoryMapper<IOException, String> {
    override fun map(src: IOException): String = when(src) {
        is FileNotCreatedException -> resource.string(R.string.not_created_file_message)
        else -> "${resource.string(R.string.some_went_wrong_with_exception_message)} ${src::javaClass}"
    }
}