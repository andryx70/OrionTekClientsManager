package com.orienteck.clients.manager.domain.model

data class Client(
    val id: Long = 0,
    val name: String,
    val email: String,
    val phone: String,
    val addresses: List<Address> = emptyList()
)