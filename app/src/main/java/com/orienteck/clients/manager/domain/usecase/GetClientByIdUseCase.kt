package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.domain.repository.ClientRepository


class GetClientByIdUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(id: Long) = repository.getClientById(id)
}