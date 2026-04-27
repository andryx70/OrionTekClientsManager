package com.orienteck.clients.manager.core

import android.content.Context
import androidx.room.Room
import com.orienteck.clients.manager.data.local.database.OrionTekDatabase
import com.orienteck.clients.manager.data.repository.ClientRepositoryImpl
import com.orienteck.clients.manager.domain.repository.ClientRepository
import com.orienteck.clients.manager.domain.usecase.AddAddressUseCase
import com.orienteck.clients.manager.domain.usecase.AddClientUseCase
import com.orienteck.clients.manager.domain.usecase.DeleteAddressUseCase
import com.orienteck.clients.manager.domain.usecase.DeleteClientUseCase
import com.orienteck.clients.manager.domain.usecase.GetClientByIdUseCase
import com.orienteck.clients.manager.domain.usecase.GetClientsUseCase
import com.orienteck.clients.manager.domain.usecase.UpdateAddressUseCase
import com.orienteck.clients.manager.domain.usecase.UpdateClientUseCase

object AppModule {

    private var database: OrionTekDatabase? = null
    private var repository: ClientRepository? = null

    fun provideDatabase(context: Context): OrionTekDatabase {
        return database ?: Room.databaseBuilder(
            context.applicationContext,
            OrionTekDatabase::class.java,
            "oriontek_database"
        ).build().also {
            database = it
        }
    }

    fun provideClientRepository(context: Context): ClientRepository {
        return repository ?: run {
            val db = provideDatabase(context)

            ClientRepositoryImpl(
                clientDao = db.clientDao(),
                addressDao = db.addressDao()
            ).also {
                repository = it
            }
        }
    }

    fun provideGetClientsUseCase(context: Context): GetClientsUseCase {
        return GetClientsUseCase(provideClientRepository(context))
    }

    fun provideGetClientByIdUseCase(context: Context): GetClientByIdUseCase {
        return GetClientByIdUseCase(provideClientRepository(context))
    }

    fun provideAddClientUseCase(context: Context): AddClientUseCase {
        return AddClientUseCase(provideClientRepository(context))
    }

    fun provideUpdateClientUseCase(context: Context): UpdateClientUseCase {
        return UpdateClientUseCase(provideClientRepository(context))
    }

    fun provideDeleteClientUseCase(context: Context): DeleteClientUseCase {
        return DeleteClientUseCase(provideClientRepository(context))
    }

    fun provideAddAddressUseCase(context: Context): AddAddressUseCase {
        return AddAddressUseCase(provideClientRepository(context))
    }

    fun provideUpdateAddressUseCase(context: Context): UpdateAddressUseCase {
        return UpdateAddressUseCase(provideClientRepository(context))
    }

    fun provideDeleteAddressUseCase(context: Context): DeleteAddressUseCase {
        return DeleteAddressUseCase(provideClientRepository(context))
    }
}