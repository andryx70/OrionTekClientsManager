package com.orienteck.clients.manager.ui.client_list

import com.orienteck.clients.manager.data.repository.FakeClientRepository

import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.usecase.DeleteClientUseCase
import com.orienteck.clients.manager.domain.usecase.GetClientsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClientListViewModelTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var getClientsUseCase: GetClientsUseCase
    private lateinit var deleteClientUseCase: DeleteClientUseCase
    private lateinit var viewModel: ClientListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = FakeClientRepository()
        getClientsUseCase = GetClientsUseCase(repository)
        deleteClientUseCase = DeleteClientUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load clients on init`() = runTest {
        repository.addClient(
            Client(
                id = 1,
                name = "Juan",
                email = "juan@test.com",
                phone = "123456"
            )
        )

        viewModel = ClientListViewModel(
            getClientsUseCase = getClientsUseCase,
            deleteClientUseCase = deleteClientUseCase
        )

        advanceUntilIdle()

        assertEquals(1, viewModel.clients.value.size)
        assertEquals("Juan", viewModel.clients.value.first().name)
    }

    @Test
    fun `should delete client successfully`() = runTest {
        val client = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456"
        )

        repository.addClient(client)

        viewModel = ClientListViewModel(
            getClientsUseCase = getClientsUseCase,
            deleteClientUseCase = deleteClientUseCase
        )

        advanceUntilIdle()

        viewModel.deleteClient(client)

        advanceUntilIdle()

        assertEquals(0, repository.getClientsList().size)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `should set error when deleting invalid client`() = runTest {
        val invalidClient = Client(
            id = 0,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456"
        )

        viewModel = ClientListViewModel(
            getClientsUseCase = getClientsUseCase,
            deleteClientUseCase = deleteClientUseCase
        )

        advanceUntilIdle()

        viewModel.deleteClient(invalidClient)

        advanceUntilIdle()

        assertEquals("Cliente inválido", viewModel.error.value)
    }
}