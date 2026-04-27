package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.repository.ClientRepository


class AddClientUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(client: Client): Long {
        require(client.name.isNotBlank()) { "El nombre es requerido" }
        require(client.email.isNotBlank()) { "El correo es requerido" }
        require(client.phone.isNotBlank()) { "El teléfono es requerido" }

        return repository.addClient(client)
    }
}