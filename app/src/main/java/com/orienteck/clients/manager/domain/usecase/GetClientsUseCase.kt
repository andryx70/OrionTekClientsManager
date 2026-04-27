package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.domain.repository.ClientRepository

class GetClientsUseCase(
    private val repository: ClientRepository
) {
    operator fun invoke() = repository.getClients()
}