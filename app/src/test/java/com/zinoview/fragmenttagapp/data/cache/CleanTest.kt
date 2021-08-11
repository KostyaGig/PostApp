package com.zinoview.fragmenttagapp.data.cache

import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [com.zinoview.fragmenttagapp.data.cache.Update]
 * @author Zinoview on 08.08.2021
 * k.gig@list.ru
 */
class CleanTest  {

    @Test
    fun test_update_data() {
        val clean = Update.Test("Hello from city this is cache")
        val expected = "Hello fr"
        val actual = clean.update("om city this is cache")
        assertEquals(expected, actual)
    }
}