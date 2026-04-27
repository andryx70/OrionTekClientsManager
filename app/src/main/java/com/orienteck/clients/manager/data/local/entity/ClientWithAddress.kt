package com.orienteck.clients.manager.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ClientWithAddresses(
    @Embedded val client: ClientEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "clientId"
    )
    val addresses: List<AddressEntity>
)