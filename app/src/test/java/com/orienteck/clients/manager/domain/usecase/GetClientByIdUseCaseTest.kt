package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Client
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetClientByIdUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: GetClientByIdUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = GetClientByIdUseCase(repository)
    }

    @Test
    fun `should return client when it exists`() = runBlocking {
        val client = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456",
            addresses = emptyList()
        )

        repository.addClient(client)

        val result = useCase(1)

        assertEquals("Juan", result?.name)
    }

    @Test
    fun `should return null when client does not exist`() = runBlocking {
        val result = useCase(999)

        assertNull(result)
    }
}