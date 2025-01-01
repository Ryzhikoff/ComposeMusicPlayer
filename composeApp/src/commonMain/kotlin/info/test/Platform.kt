package info.test

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform