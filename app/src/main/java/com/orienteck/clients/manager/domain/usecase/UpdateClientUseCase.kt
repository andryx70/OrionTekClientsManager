package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.repository.ClientRepository

class UpdateClientUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(client: Client) {
        require(client.id > 0) { "Cliente inválido" }
        require(client.name.isNotBlank()) { "El nombre es requerido" }
        require(client.email.isNotBlank()) { "El correo es requerido" }
        require(client.phone.isNotBlank()) { "El teléfono es requerido" }

        repository.updateClient(client)
    }
}