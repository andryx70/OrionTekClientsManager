package com.orienteck.clients.manager.ui.address_form

import com.orienteck.clients.manager.data.repository.FakeClientRepository
import com.orienteck.clients.manager.domain.usecase.AddAddressUseCase
import com.orienteck.clients.manager.domain.usecase.UpdateAddressUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddressFormViewModelTest {

    private lateinit var repository: FakeClientRepository
    private lateinit var addAddressUseCase: AddAddressUseCase
    private lateinit var updateAddressUseCase: UpdateAddressUseCase
    private lateinit var viewModel: AddressFormViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = FakeClientRepository()
        addAddressUseCase = AddAddressUseCase(repository)
        updateAddressUseCase = UpdateAddressUseCase(repository)

        viewModel = AddressFormViewModel(
            addAddressUseCase = addAddressUseCase,
            updateAddressUseCase = updateAddressUseCase,
            clientId = 1
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should update state when inputs change`() {
        viewModel.onStreetChange("Calle 1")
        viewModel.onCityChange("SD")
        viewModel.onCountryChange("RD")
        viewModel.onZipCodeChange("10100")

        assertEquals("Calle 1", viewModel.street.value)
        assertEquals("SD", viewModel.city.value)
        assertEquals("RD", viewModel.country.value)
        assertEquals("10100", viewModel.zipCode.value)
    }

    @Test
    fun `should set addressSaved true when save is successful`() = runTest {
        viewModel.onStreetChange("Calle 1")
        viewModel.onCityChange("SD")
        viewModel.onCountryChange("RD")
        viewModel.onZipCodeChange("10100")

        viewModel.saveAddress()

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.addressSaved.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `should set error when use case fails`() = runTest {
        val invalidViewModel = AddressFormViewModel(
            addAddressUseCase = addAddressUseCase,
            updateAddressUseCase = updateAddressUseCase,
            clientId = 0
        )

        invalidViewModel.onStreetChange("Calle 1")
        invalidViewModel.onCityChange("SD")
        invalidViewModel.onCountryChange("RD")
        invalidViewModel.onZipCodeChange("10100")

        invalidViewModel.saveAddress()

        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(invalidViewModel.error.value)
        assertFalse(invalidViewModel.addressSaved.value)
    }
}