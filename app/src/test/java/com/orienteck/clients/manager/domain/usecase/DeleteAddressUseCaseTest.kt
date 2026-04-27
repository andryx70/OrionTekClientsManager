package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Address
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DeleteAddressUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: DeleteAddressUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = DeleteAddressUseCase(repository)
    }

    @Test
    fun `should delete address when address is valid`() = runBlocking {
        val address = Address(
            id = 1,
            clientId = 1,
            street = "Calle 1",
            city = "Santo Domingo",
            country = "RD",
            zipCode = "10100"
        )

        repository.addAddress(address)

        useCase(address)

        assertEquals(0, repository.getAddresses().size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when address id is invalid`() = runBlocking {
        val address = Address(
            id = 0,
            clientId = 1,
            street = "Calle 1",
            city = "Santo Domingo",
            country = "RD",
            zipCode = "10100"
        )

        useCase(address)
    }
}