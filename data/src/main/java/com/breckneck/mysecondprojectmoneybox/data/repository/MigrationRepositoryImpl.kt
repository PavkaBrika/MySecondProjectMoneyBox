package com.breckneck.mysecondprojectmoneybox.data.repository

import com.breckneck.mysecondprojectmoneybox.data.storage.MigrationStorage
import com.breckneck.mysecondprojectmoneybox.domain.repository.MigrationRepository

class MigrationRepositoryImpl(val migrationStorage: MigrationStorage): MigrationRepository {

    override fun migration() {
        migrationStorage.migration()
    }
}