package com.orienteck.clients.manager.ui.client_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orienteck.clients.manager.domain.model.Address
import com.orienteck.clients.manager.domain.model.Client

@Composable
fun ClientDetailScreen(
    viewModel: ClientDetailViewModel,
    onBackClick: () -> Unit,
    onAddAddressClick: (Long) -> Unit,
    onEditAddressClick: (Address) -> Unit
) {
    val client by viewModel.client.collectAsState()

    ClientDetailContent(
        client = client,
        onBackClick = onBackClick,
        onAddAddressClick = onAddAddressClick,
        onEditAddressClick = onEditAddressClick,
        onDeleteAddress = { address ->
            viewModel.deleteAddress(address.id)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailContent(
    client: Client?,
    onBackClick: () -> Unit,
    onAddAddressClick: (Long) -> Unit,
    onEditAddressClick: (Address) -> Unit,
    onDeleteAddress: (Address) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Cliente") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (client == null) {
            Text(
                text = "Cliente no encontrado",
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            )
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = client.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(text = client.email)
            Text(text = client.phone)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onAddAddressClick(client.id)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar dirección")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Direcciones",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (client.addresses.isEmpty()) {
                Text("Este cliente no tiene direcciones registradas")
            } else {
                LazyColumn {
                    items(client.addresses) { address ->
                        AddressItem(
                            address = address,
                            onEditAddress = onEditAddressClick,
                            onDeleteAddress = onDeleteAddress
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddressItem(
    address: Address,
    onEditAddress: (Address) -> Unit,
    onDeleteAddress: (Address) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = address.street,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = address.city)
            Text(text = address.country)
            Text(text = address.zipCode)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        onEditAddress(address)
                    }
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = {
                        onDeleteAddress(address)
                    }
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientDetailContentPreview() {
    ClientDetailContent(
        client = Client(
            id = 1,
            name = "Juan Pérez",
            email = "juan@test.com",
            phone = "8090000000",
            addresses = listOf(
                Address(
                    id = 1,
                    clientId = 1,
                    street = "Calle Principal #10",
                    city = "Santo Domingo",
                    country = "República Dominicana",
                    zipCode = "10100"
                ),
                Address(
                    id = 2,
                    clientId = 1,
                    street = "Av. Duarte #25",
                    city = "Santiago",
                    country = "República Dominicana",
                    zipCode = "51000"
                )
            )
        ),
        onBackClick = {},
        onAddAddressClick = {},
        onEditAddressClick = {},
        onDeleteAddress = {}
    )
}