package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Address
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateAddressUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: UpdateAddressUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = UpdateAddressUseCase(repository)
    }

    @Test
    fun `should update address when data is valid`() = runBlocking {
        val originalAddress = Address(
            id = 1,
            clientId = 1,
            street = "Calle 1",
            city = "Santo Domingo",
            country = "RD",
            zipCode = "10100"
        )

        repository.addAddress(originalAddress)

        val updatedAddress = originalAddress.copy(
            street = "Calle Actualizada",
            city = "Santiago"
        )

        useCase(updatedAddress)

        val result = repository.getAddresses().first()

        assertEquals("Calle Actualizada", result.street)
        assertEquals("Santiago", result.city)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when address id is invalid`() = runBlocking {
        useCase(
            Address(
                id = 0,
                clientId = 1,
                street = "Calle 1",
                city = "Santo Domingo",
                country = "RD",
                zipCode = "10100"
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when client id is invalid`() = runBlocking {
        useCase(
            Address(
                id = 1,
                clientId = 0,
                street = "Calle 1",
                city = "Santo Domingo",
                country = "RD",
                zipCode = "10100"
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when street is empty`() = runBlocking {
        useCase(
            Address(
                id = 1,
                clientId = 1,
                street = "",
                city = "Santo Domingo",
                country = "RD",
                zipCode = "10100"
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when city is empty`() = runBlocking {
        useCase(
            Address(
                id = 1,
                clientId = 1,
                street = "Calle 1",
                city = "",
                country = "RD",
                zipCode = "10100"
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when country is empty`() = runBlocking {
        useCase(
            Address(
                id = 1,
                clientId = 1,
                street = "Calle 1",
                city = "Santo Domingo",
                country = "",
                zipCode = "10100"
            )
        )
    }
}