package com.orienteck.clients.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.orienteck.clients.manager.data.local.entity.ClientEntity
import com.orienteck.clients.manager.data.local.entity.ClientWithAddresses
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Transaction
    @Query("SELECT * FROM clients")
    fun getClientsWithAddresses(): Flow<List<ClientWithAddresses>>

    @Transaction
    @Query("SELECT * FROM clients WHERE id = :clientId")
    suspend fun getClientWithAddressesById(clientId: Long): ClientWithAddresses?

    @Insert
    suspend fun insertClient(client: ClientEntity): Long

    @Update
    suspend fun updateClient(client: ClientEntity)

    @Delete
    suspend fun deleteClient(client: ClientEntity)
}