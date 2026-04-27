package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Client
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DeleteClientUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: DeleteClientUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = DeleteClientUseCase(repository)
    }

    @Test
    fun `should delete client when client is valid`() = runBlocking {
        val client = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456",
            addresses = emptyList()
        )

        repository.addClient(client)

        useCase(client)

        assertEquals(0, repository.getClientsList().size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when client id is invalid`() = runBlocking {
        val client = Client(
            id = 0,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456",
            addresses = emptyList()
        )

        useCase(client)
    }
}