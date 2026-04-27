package com.orienteck.clients.manager.ui.client_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.domain.usecase.AddClientUseCase
import com.orienteck.clients.manager.domain.usecase.UpdateClientUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientFormViewModel(
    private val addClientUseCase: AddClientUseCase,
    private val updateClientUseCase: UpdateClientUseCase,
    private val clientToEdit: Client? = null
) : ViewModel() {

    private val _name = MutableStateFlow(clientToEdit?.name ?: "")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow(clientToEdit?.email ?: "")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phone = MutableStateFlow(clientToEdit?.phone ?: "")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _clientSaved = MutableStateFlow(false)
    val clientSaved: StateFlow<Boolean> = _clientSaved.asStateFlow()

    val isEditMode: Boolean = clientToEdit != null

    fun onNameChange(value: String) {
        _name.value = value
    }

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onPhoneChange(value: String) {
        _phone.value = value
    }

    fun saveClient() {
        viewModelScope.launch {
            try {
                val client = Client(
                    id = clientToEdit?.id ?: 0,
                    name = _name.value,
                    email = _email.value,
                    phone = _phone.value,
                    addresses = clientToEdit?.addresses ?: emptyList()
                )

                if (isEditMode) {
                    updateClientUseCase(client)
                } else {
                    addClientUseCase(client)
                }

                _clientSaved.value = true
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}