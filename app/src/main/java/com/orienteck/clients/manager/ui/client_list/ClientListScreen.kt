package com.orienteck.clients.manager.ui.client_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orienteck.clients.manager.domain.model.Client

@Composable
fun ClientListScreen(
    viewModel: ClientListViewModel,
    onAddClient: () -> Unit,
    onClientClick: (Client) -> Unit,
    onEditClientClick: (Client) -> Unit
) {
    val clients by viewModel.clients.collectAsState()
    val error by viewModel.error.collectAsState()

    ClientListContent(
        clients = clients,
        error = error,
        onDelete = { client ->
            viewModel.deleteClient(client)
        },
        onAddClient = onAddClient,
        onClientClick = onClientClick,
        onEditClientClick = onEditClientClick
    )
}

@Composable
fun ClientListContent(
    clients: List<Client>,
    error: String?,
    onDelete: (Client) -> Unit,
    onAddClient: () -> Unit,
    onClientClick: (Client) -> Unit,
    onEditClientClick: (Client) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClient) {
                Text("+")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Clientes OrionTek",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            if (clients.isEmpty()) {
                Text(text = "No hay clientes registrados")
            } else {
                LazyColumn {
                    items(clients) { client ->
                        ClientItem(
                            client = client,
                            onDelete = onDelete,
                            onClick = onClientClick,
                            onEdit = onEditClientClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClientItem(
    client: Client,
    onDelete: (Client) -> Unit,
    onClick: (Client) -> Unit,
    onEdit: (Client) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        onClick = {
            onClick(client)
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = client.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = client.email)
            Text(text = client.phone)
            Text(text = "Direcciones: ${client.addresses.size}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        onEdit(client)
                    }
                ) {
                    Text("Editar")
                }

                Button(
                    onClick = {
                        onDelete(client)
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
fun ClientListContentPreview() {
    ClientListContent(
        clients = listOf(
            Client(
                id = 1,
                name = "Juan Pérez",
                email = "juan@test.com",
                phone = "8090000000",
                addresses = emptyList()
            ),
            Client(
                id = 2,
                name = "María López",
                email = "maria@test.com",
                phone = "8091111111",
                addresses = emptyList()
            )
        ),
        error = null,
        onDelete = {},
        onAddClient = {},
        onClientClick = {},
        onEditClientClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ClientListEmptyPreview() {
    ClientListContent(
        clients = emptyList(),
        error = null,
        onDelete = {},
        onAddClient = {},
        onClientClick = {},
        onEditClientClick = {}
    )
}