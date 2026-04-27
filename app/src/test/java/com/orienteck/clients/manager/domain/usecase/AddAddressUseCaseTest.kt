package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Address
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddAddressUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: AddAddressUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = AddAddressUseCase(repository)
    }

    @Test
    fun `should add address when data is valid`() = runBlocking {
        val address = Address(
            clientId = 1,
            street = "Calle 1",
            city = "Santo Domingo",
            country = "RD",
            zipCode = "10100"
        )

        useCase(address)

        val client = repository.getClientById(1)

        assertEquals(1, repository.getAddresses().size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when clientId is invalid`() = runBlocking {
        val address = Address(
            clientId = 0,
            street = "Calle 1",
            city = "Santo Domingo",
            country = "RD",
            zipCode = "10100"
        )

        useCase(address)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when street is empty`() = runBlocking {
        val address = Address(
            clientId = 1,
            street = "",
            city = "Santo Domingo",
            country = "RD",
            zipCode = "10100"
        )

        useCase(address)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when city is empty`() = runBlocking {
        val address = Address(
            clientId = 1,
            street = "Calle 1",
            city = "",
            country = "RD",
            zipCode = "10100"
        )

        useCase(address)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when country is empty`() = runBlocking {
        val address = Address(
            clientId = 1,
            street = "Calle 1",
            city = "Santo Domingo",
            country = "",
            zipCode = "10100"
        )

        useCase(address)
    }
}