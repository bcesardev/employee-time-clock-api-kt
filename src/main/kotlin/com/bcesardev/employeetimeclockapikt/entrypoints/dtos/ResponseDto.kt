package com.bcesardev.employeetimeclockapikt.entrypoints.dtos

data class ResponseDto<T>(
        val errors: ArrayList<String> = arrayListOf(),
        var data: T? = null
)