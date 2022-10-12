package com.breckneck.mysecondprojectmoneybox.data.repository

import com.breckneck.mysecondprojectmoneybox.data.storage.MoneyboxStorage
import com.breckneck.mysecondprojectmoneybox.domain.model.MoneyboxDomain
import com.breckneck.mysecondprojectmoneybox.domain.repository.MoneyboxRepository

class MoneyboxRepositoryImpl(val moneyboxStorage: MoneyboxStorage) : MoneyboxRepository  {

    override fun createMoneybox(moneyboxDomain: MoneyboxDomain) {
        TODO("Not yet implemented")
    }

}