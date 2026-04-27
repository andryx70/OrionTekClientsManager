package com.orienteck.clients.manager.data.repository

import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.repository.ClientRepository
import kotlinx.coroutines.flow.flowOf

class FakeClientRepository : ClientRepository {

    private val clients = mutableListOf<Client>()
    private val addresses = mutableListOf<Address>()

    override fun getClients() = flowOf(clients)

    fun getAddresses(): List<Address> = addresses

    fun getClientsList(): List<Client> = clients

    override suspend fun getClientById(id: Long): Client? {
        return clients.find { it.id == id }?.copy(
            addresses = addresses.filter { it.clientId == id }
        )
    }

    override suspend fun addClient(client: Client): Long {
        val id = (clients.size + 1).toLong()
        clients.add(client.copy(id = id))
        return id
    }

    override suspend fun updateClient(client: Client) {
        val index = clients.indexOfFirst { it.id == client.id }
        if (index != -1) {
            clients[index] = client
        }
    }

    override suspend fun deleteClient(client: Client) {
        clients.removeIf { it.id == client.id }
    }

    override suspend fun addAddress(address: Address) {
        addresses.add(address.copy(id = (addresses.size + 1).toLong()))
    }

    override suspend fun updateAddress(address: Address) {
        val index = addresses.indexOfFirst { it.id == address.id }
        if (index != -1) {
            addresses[index] = address
        }
    }

    override suspend fun deleteAddress(address: Address) {
        addresses.removeIf { it.id == address.id }
    }
}