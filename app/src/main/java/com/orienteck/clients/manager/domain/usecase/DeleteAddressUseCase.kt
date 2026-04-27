package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.repository.ClientRepository

class DeleteAddressUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(address: Address) {
        require(address.id > 0) { "Dirección inválida" }

        repository.deleteAddress(address)
    }
}