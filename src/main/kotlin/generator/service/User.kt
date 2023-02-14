package generator.service

import com.google.gson.Gson
import generator.data.RandomUserModel
import generator.data.UserModel
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.io.IOException

interface UserService {
    fun getUser(seed:String): List<UserModel>
}

@Service
class UserServiceImpl: UserService{
    private val client = OkHttpClient()
    private val gson = Gson()
    private val logger = KotlinLogging.logger {}

    override fun getUser(seed: String): List<UserModel> {
        logger.info { "fun: getUser service with $seed" }

        val resp = run("https://randomuser.me/api?seed=$seed")
        val rs = gson.fromJson(resp, RandomUserModel::class.java)
        var user: List<UserModel> = listOf()
        if (rs.results.isNotEmpty()) {
            for (result in rs.results) {
                user = listOf(
                    UserModel(result.name.first, result.name.last, result.gender, result.email)
                )
            }
        }

        return user
    }

    fun run(url: String): String {
        logger.info { "fun: run with $url" }

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                logger.error { IOException("Unexpected code $response") }
                throw IOException("Unexpected code $response")
            }

            logger.info { "get random user api succeeded" }
            return response.body!!.string()
        }
    }
}
