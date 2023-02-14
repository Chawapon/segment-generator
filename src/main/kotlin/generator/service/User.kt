package generator.service

import generator.api.IRandomUser
import generator.data.UserModel
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface IUserService {
    fun getUser(seed:String): List<UserModel>
}

@Service
class UserServiceImpl: IUserService{
    @Autowired
    lateinit var randomUserImp:IRandomUser

    private val logger = KotlinLogging.logger {}

    override fun getUser(seed: String): List<UserModel> {
        logger.info { "fun: getUser service with $seed" }

        val resp = randomUserImp.getRandomUser(seed)
        var user: List<UserModel> = listOf()

        if (resp.results.isNotEmpty()) {
            for (result in resp.results) {
                user = listOf(
                    UserModel(result.name.first, result.name.last, result.gender, result.email)
                )
            }
        }

        return user
    }
}
