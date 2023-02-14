package generator

import generator.api.IRandomUser
import generator.api.RandomUserImp
import generator.data.Name
import generator.data.RandomUserModel
import generator.data.Result
import generator.data.UserModel
import generator.service.IUserService
import generator.service.UserServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.lenient
import org.mockito.Mockito.reset
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class GeneratorApplicationTests {
	@Mock
	lateinit var userService: IUserService
	@Mock
	lateinit var randomUser: IRandomUser

	@InjectMocks
	lateinit var userServiceImpl: UserServiceImpl
	@InjectMocks
	lateinit var randomUserImp: RandomUserImp

	@BeforeEach
	fun resetMocks() {
		reset(
			userService,
			randomUser
		)
	}

	@Nested
	inner class UserGenerator {
		@Test
		fun `should return list of RandomUserModel success test`() {
			val randomUsers = mockRandomUserGenerator()

			lenient().`when`(randomUser.getRandomUser("test")).thenReturn(randomUsers)

			val actual = randomUserImp.getRandomUser("test")
			Assertions.assertEquals(randomUsers, actual)
		}

		@Test
		fun `should return list of UserModel success test`() {
			val randomUsers = mockRandomUserGenerator()
			val users = mockUserGenerator()

			lenient().`when`(randomUser.getRandomUser("test")).thenReturn(randomUsers)
			lenient().`when`(userService.getUser("test")).thenReturn(users)

			val actual = userServiceImpl.getUser("test")
			Assertions.assertEquals(users, actual)
		}
	}

	fun mockUserGenerator(): List<UserModel> {
		return listOf(mockUserModel())
	}

	fun mockRandomUserGenerator(): RandomUserModel {
		return mockRandomUserModel()
	}

	fun mockUserModel(
		firstname: String = "Areta",
		lastName: String = "Araújo",
		email: String = "areta.araujo@example.com",
		gender: String = "female"
	): UserModel {
		return UserModel(firstname, lastName, gender, email)
	}

	fun mockRandomUserModel(
		results: List<Result> = listOf(Result(Name("Areta", "Araújo"), "female", "areta.araujo@example.com"))
	): RandomUserModel {
		return RandomUserModel(results)
	}
}