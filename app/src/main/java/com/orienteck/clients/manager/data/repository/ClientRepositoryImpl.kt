package com.orienteck.clients.manager.data.repository

import com.orienteck.clients.manager.data.local.dao.AddressDao
import com.orienteck.clients.manager.data.local.dao.ClientDao
import com.orienteck.clients.manager.data.mapper.toDomain
import com.orienteck.clients.manager.data.mapper.toEntity
import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClientRepositoryImpl(
    private val clientDao: ClientDao,
    private val addressDao: AddressDao
) : ClientRepository {

    override fun getClients(): Flow<List<Client>> {
        return clientDao.getClientsWithAddresses()
            .map { clients ->
                clients.map { it.toDomain() }
            }
    }

    override suspend fun getClientById(id: Long): Client? {
        return clientDao.getClientWithAddressesById(id)?.toDomain()
    }

    override suspend fun addClient(client: Client): Long {
        return clientDao.insertClient(client.toEntity())
    }

    override suspend fun updateClient(client: Client) {
        clientDao.updateClient(client.toEntity())
    }

    override suspend fun deleteClient(client: Client) {
        clientDao.deleteClient(client.toEntity())
    }

    override suspend fun addAddress(address: Address) {
        addressDao.insertAddress(address.toEntity())
    }

    override suspend fun updateAddress(address: Address) {
        addressDao.updateAddress(address.toEntity())
    }

    override suspend fun deleteAddress(address: Address) {
        addressDao.deleteAddress(address.toEntity())
    }
}