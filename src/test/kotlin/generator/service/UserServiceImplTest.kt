package generator.service

import generator.data.UserModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UserServiceImplTest{

    @Test
    fun testRunSuccess() {
        // given
        val url = "https://randomuser.me/api?seed=unitTest"
        val service = mockk<UserServiceImpl>("RandomUserAPIMock")
        val respBodyMock = """{"results":[{"gender":"female","name":{"title":"Miss","first":"آیناز","last":"کریمی"},"location":{"street":{"number":9617,"name":"نام قدیم میدان های تهران"},"city":"تهران","state":"گلستان","country":"Iran","postcode":66909,"coordinates":{"latitude":"17.2334","longitude":"88.9196"},"timezone":{"offset":"+4:30","description":"Kabul"}},"email":"aynz.khrymy@example.com","login":{"uuid":"2d5afd5e-a598-469f-9933-4edba18bcefb","username":"blueelephant921","password":"cccc","salt":"uJHyJ9ZL","md5":"1c609a27f083f9557166876073ffc83b","sha1":"f57a35f6b04535b2f3c7024b5befdbcb84e47d7e","sha256":"cb364efc8489b343c746a11bee2de4fd6f83c38c4811a6b84b1526a74672e8f5"},"dob":{"date":"1992-02-07T17:56:13.316Z","age":31},"registered":{"date":"2016-03-25T07:13:10.667Z","age":6},"phone":"005-78896313","cell":"0964-597-8705","id":{"name":"","value":null},"picture":{"large":"https://randomuser.me/api/portraits/women/62.jpg","medium":"https://randomuser.me/api/portraits/med/women/62.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/women/62.jpg"},"nat":"IR"}],"info":{"seed":"foobar","results":1,"page":1,"version":"1.4"}}"""
        val resp = Response.Builder().code(200).message("Success").body(respBodyMock.toResponseBody())
        every { service.run(url) } returns resp.toString()

        // when
        val result = service.run(url)

        // then
        verify { service.run(url) }
        assertEquals(resp.toString(), result)
    }

    @Test
    fun testRunError() {
        // given
        val url = "https://randomuser.me/api?seed=unitTest"
        val service = mockk<UserServiceImpl>("RandomUserAPIMock")
        val respBodyMock = """{"error": "Uh oh, something has gone wrong. Please tweet us @randomapi about the issue. Thank you."}"""
        val resp = Response.Builder().code(500).body(respBodyMock.toResponseBody())
        every { service.run(url) } returns resp.toString()

        // when
        val result = service.run(url)

        // then
        verify { service.run(url) }
        assertEquals(resp.toString(), result)
    }

    @Test
    fun testGetUserSuccessThenIsNotEmpty() {
        // given
        val seed = "unitTest"
        val service = mockk<UserServiceImpl>("GetUserAPIMock")
        val userMock: List<UserModel> = listOf(
            UserModel("A", "A", "Male", "test@unit.com"),
            UserModel("B", "B", "Female", "test@unit.com")
        )
        every { service.getUser(seed) } returns userMock

        // when
        val result = service.getUser(seed)

        // then
        verify { service.getUser(seed) }
        assertEquals(userMock, result)
    }

    @Test
    fun testGetUserSuccessButEmpty() {
        // given
        val seed = "unitTest"
        val service = mockk<UserServiceImpl>("GetUserAPIMock")
        val userMock: List<UserModel> = listOf()
        every { service.getUser(seed) } returns userMock

        // when
        val result = service.getUser(seed)

        // then
        verify { service.getUser(seed) }
        assertEquals(userMock, result)
    }
}
