package com.orienteck.clients.manager.domain.repository

import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun getClients(): Flow<List<Client>>
    suspend fun getClientById(id: Long): Client?
    suspend fun addClient(client: Client): Long
    suspend fun updateClient(client: Client)
    suspend fun deleteClient(client: Client)
    suspend fun addAddress(address: Address)
    suspend fun updateAddress(address: Address)
    suspend fun deleteAddress(address: Address)
}