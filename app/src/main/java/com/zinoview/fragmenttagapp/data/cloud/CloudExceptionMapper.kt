package com.zinoview.fragmenttagapp.data.cloud

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.domain.GenericException
import com.zinoview.fragmenttagapp.domain.NoConnectionException
import com.zinoview.fragmenttagapp.domain.ServiceUnavailableException
import java.io.IOException
import java.net.HttpRetryException
import java.net.UnknownHostException


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */
class CloudExceptionMapper : Abstract.FactoryMapper<Exception, IOException> {
    override fun map(src: Exception): IOException = when(src) {
        is UnknownHostException -> NoConnectionException()
        is HttpRetryException -> ServiceUnavailableException()
        else -> GenericException()
    }
}