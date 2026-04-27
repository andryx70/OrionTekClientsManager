package com.orienteck.clients.manager.ui.address_form

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddressFormScreen(
    viewModel: AddressFormViewModel,
    onAddressSaved: () -> Unit,
    onBackClick: () -> Unit
) {
    val street by viewModel.street.collectAsState()
    val city by viewModel.city.collectAsState()
    val country by viewModel.country.collectAsState()
    val zipCode by viewModel.zipCode.collectAsState()
    val error by viewModel.error.collectAsState()
    val addressSaved by viewModel.addressSaved.collectAsState()

    LaunchedEffect(addressSaved) {
        if (addressSaved) {
            onAddressSaved()
        }
    }

    AddressFormContent(
        title = if (viewModel.isEditMode) "Editar Dirección" else "Nueva Dirección",
        buttonText = if (viewModel.isEditMode) "Actualizar Dirección" else "Guardar Dirección",
        street = street,
        city = city,
        country = country,
        zipCode = zipCode,
        error = error,
        onStreetChange = viewModel::onStreetChange,
        onCityChange = viewModel::onCityChange,
        onCountryChange = viewModel::onCountryChange,
        onZipCodeChange = viewModel::onZipCodeChange,
        onSaveClick = viewModel::saveAddress,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressFormContent(
    title: String,
    buttonText: String,
    street: String,
    city: String,
    country: String,
    zipCode: String,
    error: String?,
    onStreetChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
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
                value = street,
                onValueChange = onStreetChange,
                label = { Text("Calle") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = city,
                onValueChange = onCityChange,
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = country,
                onValueChange = onCountryChange,
                label = { Text("País") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = zipCode,
                onValueChange = onZipCodeChange,
                label = { Text("Código postal") },
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
fun AddressFormContentPreview() {
    AddressFormContent(
        title = "Nueva Dirección",
        buttonText = "Guardar Dirección",
        street = "Calle Principal #10",
        city = "Santo Domingo",
        country = "República Dominicana",
        zipCode = "10100",
        error = null,
        onStreetChange = {},
        onCityChange = {},
        onCountryChange = {},
        onZipCodeChange = {},
        onSaveClick = {},
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AddressFormContentEditPreview() {
    AddressFormContent(
        title = "Editar Dirección",
        buttonText = "Actualizar Dirección",
        street = "Av. Duarte #25",
        city = "Santiago",
        country = "República Dominicana",
        zipCode = "51000",
        error = null,
        onStreetChange = {},
        onCityChange = {},
        onCountryChange = {},
        onZipCodeChange = {},
        onSaveClick = {},
        onBackClick = {}
    )
}