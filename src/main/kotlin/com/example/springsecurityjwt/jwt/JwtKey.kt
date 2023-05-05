package com.example.springsecurityjwt.jwt

object JwtKey {
    private val JWT_KEY_SETS = JwtKeySets(
        listOf(
            JwtKeySets.JwtKeySet(
                kid = "kid-1",
                secretKey = "cxpoi2thr0w0gxgrz0w4yj59v7kk4sae",
            ),
            JwtKeySets.JwtKeySet(
                kid = "kid-2",
                secretKey = "oyuq98vmooc59h1iwfhb6kgczyt71p21",
            ),
            JwtKeySets.JwtKeySet(
                kid = "kid-3",
                secretKey = "lhfz5s7lhk5215l4gco26zioo5no5r3a",
            ),
        ),
    )

    fun getRandomJwtKeySet() = JWT_KEY_SETS.getRandomJwtKeySet()

    fun getJwtKeySetByKid(kid: String) = JWT_KEY_SETS.getJwtKeySetByKid(kid)

}
