package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Client
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetClientsUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: GetClientsUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = GetClientsUseCase(repository)
    }

    @Test
    fun `should return clients when repository has data`() = runBlocking {
        val client1 = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456",
            addresses = emptyList()
        )

        val client2 = Client(
            id = 2,
            name = "Maria",
            email = "maria@test.com",
            phone = "789101",
            addresses = emptyList()
        )

        repository.addClient(client1)
        repository.addClient(client2)

        val result = useCase().first()

        assertEquals(2, result.size)
    }

    @Test
    fun `should return empty list when no clients exist`() = runBlocking {
        val result = useCase().first()

        assertEquals(0, result.size)
    }
}