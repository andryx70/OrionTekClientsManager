package com.orienteck.clients.manager.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orienteck.clients.manager.data.local.dao.AddressDao
import com.orienteck.clients.manager.data.local.dao.ClientDao
import com.orienteck.clients.manager.data.local.entity.AddressEntity
import com.orienteck.clients.manager.data.local.entity.ClientEntity

@Database(
    entities = [ClientEntity::class, AddressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OrionTekDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun addressDao(): AddressDao
}