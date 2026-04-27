package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.repository.ClientRepository

class UpdateAddressUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(address: Address) {
        require(address.id > 0) { "Dirección inválida" }
        require(address.clientId > 0) { "Cliente inválido" }
        require(address.street.isNotBlank()) { "La calle es requerida" }
        require(address.city.isNotBlank()) { "La ciudad es requerida" }
        require(address.country.isNotBlank()) { "El país es requerido" }

        repository.updateAddress(address)
    }
}