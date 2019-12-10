package com.bcesardev.employeetimeclockapikt.business.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun generateBCrypt(password: String): String = BCryptPasswordEncoder().encode(password)