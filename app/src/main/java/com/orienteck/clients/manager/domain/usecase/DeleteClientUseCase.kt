package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.repository.ClientRepository


class DeleteClientUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(client: Client) {
        require(client.id > 0) { "Cliente inválido" }

        repository.deleteClient(client)
    }
}