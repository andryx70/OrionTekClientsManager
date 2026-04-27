package com.orienteck.clients.manager.domain.usecase

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Client
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddClientUseCaseTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var useCase: AddClientUseCase

    @Before
    fun setup() {
        repository = FakeClientRepository()
        useCase = AddClientUseCase(repository)
    }

    @Test
    fun `should add client when data is valid`() = runBlocking {
        val client = Client(
            name = "Juan",
            email = "juan@test.com",
            phone = "123456"
        )

        val id = useCase(client)

        assertEquals(1L, id)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should fail when name is empty`(): Unit = runBlocking {
        val client = Client(
            name = "",
            email = "juan@test.com",
            phone = "123456"
        )

        useCase(client)
    }
}