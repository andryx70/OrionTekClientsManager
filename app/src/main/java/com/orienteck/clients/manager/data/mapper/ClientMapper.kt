package com.orienteck.clients.manager.data.mapper

import com.orienteck.clients.manager.data.local.entity.ClientEntity
import com.orienteck.clients.manager.data.local.entity.ClientWithAddresses
import com.orienteck.clients.manager.domain.model.Client


fun ClientEntity.toDomain() = Client(
    id = id,
    name = name,
    email = email,
    phone = phone
)

fun Client.toEntity() = ClientEntity(
    id = id,
    name = name,
    email = email,
    phone = phone
)

fun ClientWithAddresses.toDomain() = Client(
    id = client.id,
    name = client.name,
    email = client.email,
    phone = client.phone,
    addresses = addresses.map { it.toDomain() }
)