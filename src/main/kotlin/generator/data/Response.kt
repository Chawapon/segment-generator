package generator.data

data class UserModel(
    var Firstname: String,
    var Lastname: String,
    var Gender: String,
    var Email: String
)

data class RandomUserModel(
    var results: List<Result>
)

data class Result(
    var name: Name,
    var gender: String,
    var email: String
)

data class Name(
    var first: String,
    var last: String
)