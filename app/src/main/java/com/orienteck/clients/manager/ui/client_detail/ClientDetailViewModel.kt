package com.orienteck.clients.manager.ui.client_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.usecase.GetClientByIdUseCase
import com.orienteck.clients.manager.domain.usecase.DeleteAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientDetailViewModel(
    private val getClientByIdUseCase: GetClientByIdUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val clientId: Long
) : ViewModel() {

    private val _client = MutableStateFlow<Client?>(null)
    val client: StateFlow<Client?> = _client.asStateFlow()

    init {
        loadClient()
    }

    private fun loadClient() {
        viewModelScope.launch {
            _client.value = getClientByIdUseCase(clientId)
        }
    }

    fun deleteAddress(addressId: Long) {
        val current = _client.value ?: return

        val address = current.addresses.find { it.id == addressId } ?: return

        viewModelScope.launch {
            deleteAddressUseCase(address)
            loadClient()
        }
    }
}