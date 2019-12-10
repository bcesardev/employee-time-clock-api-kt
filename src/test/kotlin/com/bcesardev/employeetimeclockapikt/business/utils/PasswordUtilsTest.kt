package com.bcesardev.employeetimeclockapikt.business.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class PasswordUtilsTest {

    private val PASSWORD = "123456"
    private val bCryptEncoder = BCryptPasswordEncoder()

    @Test
    fun testGenerateHashPassword() {
        val hash = generateBCrypt(PASSWORD)
        Assertions.assertTrue(bCryptEncoder.matches(PASSWORD, hash))
    }
}