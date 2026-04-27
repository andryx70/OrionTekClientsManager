package com.orienteck.clients.manager.ui.client_detail

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.usecase.DeleteAddressUseCase
import com.orienteck.clients.manager.domain.usecase.GetClientByIdUseCase
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
class ClientDetailViewModelTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var getClientByIdUseCase: GetClientByIdUseCase
    private lateinit var deleteAddressUseCase: DeleteAddressUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = FakeClientRepository()
        getClientByIdUseCase = GetClientByIdUseCase(repository)
        deleteAddressUseCase = DeleteAddressUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load client on init`() = runTest {
        val client = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456"
        )

        repository.addClient(client)

        val viewModel = ClientDetailViewModel(
            getClientByIdUseCase = getClientByIdUseCase,
            deleteAddressUseCase = deleteAddressUseCase,
            clientId = 1
        )

        advanceUntilIdle()

        assertEquals("Juan", viewModel.client.value?.name)
    }

    @Test
    fun `should delete existing address`() = runTest {
        val client = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456"
        )

        val address = Address(
            id = 1,
            clientId = 1,
            street = "Calle 1",
            city = "Santo Domingo",
            country = "RD",
            zipCode = "10100"
        )

        repository.addClient(client)
        repository.addAddress(address)

        val viewModel = ClientDetailViewModel(
            getClientByIdUseCase = getClientByIdUseCase,
            deleteAddressUseCase = deleteAddressUseCase,
            clientId = 1
        )

        advanceUntilIdle()

        viewModel.deleteAddress(1)

        advanceUntilIdle()

        assertEquals(0, viewModel.client.value?.addresses?.size)
    }

    @Test
    fun `should do nothing when client is null`() = runTest {
        val viewModel = ClientDetailViewModel(
            getClientByIdUseCase = getClientByIdUseCase,
            deleteAddressUseCase = deleteAddressUseCase,
            clientId = 999
        )

        advanceUntilIdle()

        viewModel.deleteAddress(1)

        advanceUntilIdle()

        assertNull(viewModel.client.value)
    }

    @Test
    fun `should do nothing when address does not exist`() = runTest {
        val client = Client(
            id = 1,
            name = "Juan",
            email = "juan@test.com",
            phone = "123456"
        )

        repository.addClient(client)

        val viewModel = ClientDetailViewModel(
            getClientByIdUseCase = getClientByIdUseCase,
            deleteAddressUseCase = deleteAddressUseCase,
            clientId = 1
        )

        advanceUntilIdle()

        viewModel.deleteAddress(999)

        advanceUntilIdle()

        assertEquals(0, viewModel.client.value?.addresses?.size)
    }
}