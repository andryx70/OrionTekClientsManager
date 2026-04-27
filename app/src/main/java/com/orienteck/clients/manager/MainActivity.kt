package com.orienteck.clients.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.orienteck.clients.manager.core.AppModule
import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.model.Client
import com.orienteck.clients.manager.ui.address_form.AddressFormScreen
import com.orienteck.clients.manager.ui.address_form.AddressFormViewModel
import com.orienteck.clients.manager.ui.client_detail.ClientDetailScreen
import com.orienteck.clients.manager.ui.client_detail.ClientDetailViewModel
import com.orienteck.clients.manager.ui.client_form.ClientFormScreen
import com.orienteck.clients.manager.ui.client_form.ClientFormViewModel
import com.orienteck.clients.manager.ui.client_list.ClientListScreen
import com.orienteck.clients.manager.ui.client_list.ClientListViewModel
import com.orienteck.clients.manager.ui.theme.OrionTekClientsManagerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getClientsUseCase = AppModule.provideGetClientsUseCase(this)
        val getClientByIdUseCase = AppModule.provideGetClientByIdUseCase(this)

        val addClientUseCase = AppModule.provideAddClientUseCase(this)
        val updateClientUseCase = AppModule.provideUpdateClientUseCase(this)
        val deleteClientUseCase = AppModule.provideDeleteClientUseCase(this)

        val addAddressUseCase = AppModule.provideAddAddressUseCase(this)
        val updateAddressUseCase = AppModule.provideUpdateAddressUseCase(this)
        val deleteAddressUseCase = AppModule.provideDeleteAddressUseCase(this)

        setContent {
            OrionTekClientsManagerTheme {

                var currentScreen by remember { mutableStateOf("list") }
                var selectedClientId by remember { mutableLongStateOf(0L) }
                var selectedClient by remember { mutableStateOf<Client?>(null) }
                var selectedAddress by remember { mutableStateOf<Address?>(null) }

                when (currentScreen) {

                    "list" -> {
                        val viewModel = ClientListViewModel(
                            getClientsUseCase = getClientsUseCase,
                            deleteClientUseCase = deleteClientUseCase
                        )

                        ClientListScreen(
                            viewModel = viewModel,
                            onAddClient = {
                                selectedClient = null
                                currentScreen = "form"
                            },
                            onClientClick = { client ->
                                selectedClientId = client.id
                                currentScreen = "detail"
                            },
                            onEditClientClick = { client ->
                                selectedClient = client
                                currentScreen = "form"
                            }
                        )
                    }

                    "form" -> {
                        val viewModel = ClientFormViewModel(
                            addClientUseCase = addClientUseCase,
                            updateClientUseCase = updateClientUseCase,
                            clientToEdit = selectedClient
                        )

                        ClientFormScreen(
                            viewModel = viewModel,
                            onClientSaved = {
                                selectedClient = null
                                currentScreen = "list"
                            },
                            onBackClick = {
                                selectedClient = null
                                currentScreen = "list"
                            }
                        )
                    }

                    "detail" -> {
                        val viewModel = ClientDetailViewModel(
                            getClientByIdUseCase = getClientByIdUseCase,
                            deleteAddressUseCase = deleteAddressUseCase,
                            clientId = selectedClientId
                        )

                        ClientDetailScreen(
                            viewModel = viewModel,
                            onBackClick = {
                                currentScreen = "list"
                            },
                            onAddAddressClick = { clientId ->
                                selectedClientId = clientId
                                selectedAddress = null
                                currentScreen = "address_form"
                            },
                            onEditAddressClick = { address ->
                                selectedAddress = address
                                selectedClientId = address.clientId
                                currentScreen = "address_form"
                            }
                        )
                    }

                    "address_form" -> {
                        val viewModel = AddressFormViewModel(
                            addAddressUseCase = addAddressUseCase,
                            updateAddressUseCase = updateAddressUseCase,
                            clientId = selectedClientId,
                            addressToEdit = selectedAddress
                        )

                        AddressFormScreen(
                            viewModel = viewModel,
                            onAddressSaved = {
                                selectedAddress = null
                                currentScreen = "detail"
                            },
                            onBackClick = {
                                selectedAddress = null
                                currentScreen = "detail"
                            }
                        )
                    }
                }
            }
        }
    }
}