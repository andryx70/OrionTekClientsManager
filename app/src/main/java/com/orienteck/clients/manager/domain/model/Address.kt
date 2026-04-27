package com.orienteck.clients.manager.domain.model

data class Address(
    val id: Long = 0,
    val clientId: Long,
    val street: String,
    val city: String,
    val country: String,
    val zipCode: String
)