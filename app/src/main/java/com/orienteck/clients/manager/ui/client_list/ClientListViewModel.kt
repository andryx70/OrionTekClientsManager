package com.orienteck.clients.manager.ui.client_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.usecase.DeleteClientUseCase
import com.orienteck.clients.manager.domain.usecase.GetClientsUseCase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientListViewModel(
    private val getClientsUseCase: GetClientsUseCase,
    private val deleteClientUseCase: DeleteClientUseCase
) : ViewModel() {

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadClients()
    }

    private fun loadClients() {
        viewModelScope.launch {
            getClientsUseCase().collect { clientList ->
                _clients.value = clientList
            }
        }
    }

    fun deleteClient(client: Client) {
        viewModelScope.launch {
            try {
                deleteClientUseCase(client)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}