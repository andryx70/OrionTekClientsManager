package com.orienteck.clients.manager.ui.client_form

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ClientFormScreen(
    viewModel: ClientFormViewModel,
    onClientSaved: () -> Unit,
    onBackClick: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val error by viewModel.error.collectAsState()
    val clientSaved by viewModel.clientSaved.collectAsState()

    LaunchedEffect(clientSaved) {
        if (clientSaved) {
            onClientSaved()
        }
    }

    ClientFormContent(
        title = if (viewModel.isEditMode) "Editar Cliente" else "Nuevo Cliente",
        buttonText = if (viewModel.isEditMode) "Actualizar Cliente" else "Guardar Cliente",
        name = name,
        email = email,
        phone = phone,
        error = error,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = viewModel::onPhoneChange,
        onSaveClick = viewModel::saveClient,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientFormContent(
    title: String,
    buttonText: String,
    name: String,
    email: String,
    phone: String,
    error: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
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

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = onPhoneChange,
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientFormContentPreview() {
    ClientFormContent(
        title = "Nuevo Cliente",
        buttonText = "Guardar Cliente",
        name = "Juan Pérez",
        email = "juan@test.com",
        phone = "8090000000",
        error = null,
        onNameChange = {},
        onEmailChange = {},
        onPhoneChange = {},
        onSaveClick = {},
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ClientFormContentEditPreview() {
    ClientFormContent(
        title = "Editar Cliente",
        buttonText = "Actualizar Cliente",
        name = "María López",
        email = "maria@test.com",
        phone = "8091111111",
        error = null,
        onNameChange = {},
        onEmailChange = {},
        onPhoneChange = {},
        onSaveClick = {},
        onBackClick = {}
    )
}