package com.orienteck.clients.manager.ui.address_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.usecase.AddAddressUseCase
import com.orienteck.clients.manager.domain.usecase.UpdateAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressFormViewModel(
    private val addAddressUseCase: AddAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val clientId: Long,
    private val addressToEdit: Address? = null
) : ViewModel() {

    private val _street = MutableStateFlow(addressToEdit?.street ?: "")
    val street = _street.asStateFlow()

    private val _city = MutableStateFlow(addressToEdit?.city ?: "")
    val city = _city.asStateFlow()

    private val _country = MutableStateFlow(addressToEdit?.country ?: "")
    val country = _country.asStateFlow()

    private val _zipCode = MutableStateFlow(addressToEdit?.zipCode ?: "")
    val zipCode = _zipCode.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _addressSaved = MutableStateFlow(false)
    val addressSaved = _addressSaved.asStateFlow()

    val isEditMode: Boolean = addressToEdit != null

    fun onStreetChange(value: String) { _street.value = value }
    fun onCityChange(value: String) { _city.value = value }
    fun onCountryChange(value: String) { _country.value = value }
    fun onZipCodeChange(value: String) { _zipCode.value = value }

    fun saveAddress() {
        viewModelScope.launch {
            try {
                val address = Address(
                    id = addressToEdit?.id ?: 0,
                    clientId = clientId,
                    street = _street.value,
                    city = _city.value,
                    country = _country.value,
                    zipCode = _zipCode.value
                )

                if (isEditMode) {
                    updateAddressUseCase(address)
                } else {
                    addAddressUseCase(address)
                }

                _addressSaved.value = true
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}