package com.example.pouchy.api

data class AdviceResponse(
    val slip: Slip
)

data class Slip(
    val advice: String
)

