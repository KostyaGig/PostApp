package com.zinoview.fragmenttagapp.data.cache

import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [com.zinoview.fragmenttagapp.data.cache.Clean]
 * @author Zinoview on 08.08.2021
 * k.gig@list.ru
 */
class CleanTest  {

    @Test
    fun test_update_data() {
        val clean = Clean.Test("Hello from city this is cache")
        val expected = "Hello fr"
        val actual = clean.updateFile("om city this is cache")
        assertEquals(expected, actual)
    }
}