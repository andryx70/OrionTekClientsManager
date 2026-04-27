package com.orienteck.clients.manager.data.mapper

import com.orienteck.clients.manager.data.local.entity.AddressEntity
import com.orienteck.clients.manager.domain.model.Address


fun AddressEntity.toDomain() = Address(
    id = id,
    clientId = clientId,
    street = street,
    city = city,
    country = country,
    zipCode = zipCode
)

fun Address.toEntity() = AddressEntity(
    id = id,
    clientId = clientId,
    street = street,
    city = city,
    country = country,
    zipCode = zipCode
)