package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.presentation.post.PostError
import java.io.IOException


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */

class IOExceptionMapper(
    private val resource: Resource
) : Abstract.FactoryMapper<IOException, PostError> {
    override fun map(src: IOException): PostError = when(src) {
        is NoConnectionException -> PostError.NoConnectionError(resource.string(R.string.no_connection_message))
        is ServiceUnavailableException -> PostError.ServiceUnavailableError(resource.string(R.string.service_unavailable_message))
        else  -> PostError.GenericError(resource.string(R.string.some_went_wrong_message))
    }
}