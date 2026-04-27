package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Client
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateClientUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: UpdateClientUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = UpdateClientUseCase(repository)
    }

    @Test
    fun `should update client when data is valid`() = runBlocking {
        val originalClient = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456",
            addresses = emptyList()
        )

        repository.addClient(originalClient)

        val updatedClient = originalClient.copy(
            name = "Juan Actualizado",
            email = "nuevo@test.com"
        )

        useCase(updatedClient)

        val result = repository.getClientsList().first()

        assertEquals("Juan Actualizado", result.name)
        assertEquals("nuevo@test.com", result.email)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when client id is invalid`() = runBlocking {
        useCase(
            Client(
                id = 0,
                name = "Juan",
                email = "juan@test.com",
                phone = "123456"
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when name is empty`() = runBlocking {
        useCase(
            Client(
                id = 1,
                name = "",
                email = "juan@test.com",
                phone = "123456"
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when email is empty`() = runBlocking {
        useCase(
            Client(
                id = 1,
                name = "Juan",
                email = "",
                phone = "123456"
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when phone is empty`() = runBlocking {
        useCase(
            Client(
                id = 1,
                name = "Juan",
                email = "juan@test.com",
                phone = ""
            )
        )
    }
}