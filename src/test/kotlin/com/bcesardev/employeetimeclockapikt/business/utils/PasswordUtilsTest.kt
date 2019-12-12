package com.bcesardev.employeetimeclockapikt.business.utils

import com.mongodb.internal.connection.tlschannel.util.Util.assertTrue
import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordUtilsTest {

    private val PASSWORD = "123456"
    private val bCryptEncoder = BCryptPasswordEncoder()

    @Test
    fun testGenerateHashPassword() {
        val hash = generateBCrypt(PASSWORD)
        assertTrue(bCryptEncoder.matches(PASSWORD, hash))
    }
}