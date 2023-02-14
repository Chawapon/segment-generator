package generator.api

import com.google.gson.Gson
import generator.data.RandomUserModel
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.springframework.stereotype.Service

interface IRandomUser {
    fun getRandomUser(seed:String): RandomUserModel
}

object RandomUserAPI {
    const val url = "https://randomuser.me/api"
}

@Service
class RandomUserImp: IRandomUser {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val logger = KotlinLogging.logger {}

    override fun getRandomUser(seed: String): RandomUserModel {
        logger.info { "fun: run with $seed" }

        val request = Request.Builder()
                .url(RandomUserAPI.url + "?seed=$seed")
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                logger.error { IOException("Unexpected code $response") }
                throw IOException("Unexpected code $response")
            }
            logger.info { "get random user api succeeded" }

            return gson.fromJson(response.body!!.string(), RandomUserModel::class.java)
        }
    }

}