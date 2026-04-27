package com.orienteck.clients.manager.ui.client_form

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.usecase.AddClientUseCase
import com.orienteck.clients.manager.domain.usecase.UpdateClientUseCase
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
class ClientFormViewModelTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var addClientUseCase: AddClientUseCase
    private lateinit var updateClientUseCase: UpdateClientUseCase
    private lateinit var viewModel: ClientFormViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = FakeClientRepository()
        addClientUseCase = AddClientUseCase(repository)
        updateClientUseCase = UpdateClientUseCase(repository)

        viewModel = ClientFormViewModel(
            addClientUseCase = addClientUseCase,
            updateClientUseCase = updateClientUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update state when inputs change`() {
        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("juan@test.com")
        viewModel.onPhoneChange("123456")

        assertEquals("Juan", viewModel.name.value)
        assertEquals("juan@test.com", viewModel.email.value)
        assertEquals("123456", viewModel.phone.value)
    }

    @Test
    fun `should set clientSaved true when save is successful`() = runTest {
        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("juan@test.com")
        viewModel.onPhoneChange("123456")

        viewModel.saveClient()

        advanceUntilIdle()

        assertTrue(viewModel.clientSaved.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `should load client data when editing`() {
        val client = Client(
            id = 1,
            name = "Maria",
            email = "maria@test.com",
            phone = "8091111111"
        )

        val editViewModel = ClientFormViewModel(
            addClientUseCase = addClientUseCase,
            updateClientUseCase = updateClientUseCase,
            clientToEdit = client
        )

        assertTrue(editViewModel.isEditMode)
        assertEquals("Maria", editViewModel.name.value)
        assertEquals("maria@test.com", editViewModel.email.value)
        assertEquals("8091111111", editViewModel.phone.value)
    }

    @Test
    fun `should update client when edit mode is active`() = runTest {
        val client = Client(
            id = 1,
            name = "Maria",
            email = "maria@test.com",
            phone = "8091111111"
        )

        repository.addClient(client)

        val editViewModel = ClientFormViewModel(
            addClientUseCase = addClientUseCase,
            updateClientUseCase = updateClientUseCase,
            clientToEdit = client
        )

        editViewModel.onNameChange("Maria Actualizada")
        editViewModel.onEmailChange("maria.new@test.com")
        editViewModel.saveClient()

        advanceUntilIdle()

        val updatedClient = repository.getClientsList().first()

        assertTrue(editViewModel.clientSaved.value)
        assertEquals("Maria Actualizada", updatedClient.name)
        assertEquals("maria.new@test.com", updatedClient.email)
    }

    @Test
    fun `should set error when name is empty`() = runTest {
        viewModel.onNameChange("")
        viewModel.onEmailChange("juan@test.com")
        viewModel.onPhoneChange("123456")

        viewModel.saveClient()

        advanceUntilIdle()

        assertFalse(viewModel.clientSaved.value)
        assertEquals("El nombre es requerido", viewModel.error.value)
    }

    @Test
    fun `should set error when email is empty`() = runTest {
        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("")
        viewModel.onPhoneChange("123456")

        viewModel.saveClient()

        advanceUntilIdle()

        assertFalse(viewModel.clientSaved.value)
        assertEquals("El correo es requerido", viewModel.error.value)
    }

    @Test
    fun `should set error when phone is empty`() = runTest {
        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("juan@test.com")
        viewModel.onPhoneChange("")

        viewModel.saveClient()

        advanceUntilIdle()

        assertFalse(viewModel.clientSaved.value)
        assertEquals("El teléfono es requerido", viewModel.error.value)
    }
}