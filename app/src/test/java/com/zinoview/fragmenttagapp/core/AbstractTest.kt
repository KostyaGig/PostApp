package com.zinoview.fragmenttagapp.core

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class AbstractTest {

    @Test
    fun test_map_success() {
        val serverModel = ServerModel(1,1,"title1","body1")
        val commonModel = serverModel.map(TestMapper())

        val expected = "id: 1, title: title1"
        val actual = commonModel.description
        assertEquals(expected, actual)
    }

    @Test
    fun test_map_fail() {
        val serverModel = ServerModel(2,2,"title2","body2")
        val commonModel = serverModel.map(TestMapper())

        val expected = "id:2,title:title2"
        val actual = commonModel.description
        assertEquals(expected, actual)
    }

    @Test
    fun test_factory_success() {
        val wordNumber = "one"
        val numberFactory = TestFactory()

        val expected = 1
        val actual = numberFactory.map(wordNumber)
        assertEquals(expected, actual)
    }


}

data class ServerModel(
    private val id: Int,
    private val userId: Int,
    private val title: String,
    private val body: String
) : Abstract.Object{
    override fun <T> map(mapper: Abstract.PostMapper<T>): T = mapper.map(id, userId, title, body)
}

data class CommonModel(
    val description: String
)

class TestMapper : Abstract.PostMapper<CommonModel> {
    override fun map(id: Int, userId: Int, title: String, body: String): CommonModel
        = CommonModel("id: $id, title: $title")
}

class TestFactory : Abstract.FactoryMapper<String,Int> {
    override fun map(src: String): Int = when(src) {
        "one" -> 1
        "two" -> 2
        else -> throw IllegalArgumentException("not valid arguments factory")
    }
}