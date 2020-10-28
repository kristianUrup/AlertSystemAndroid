package com.example.machinealertsubscription.BE

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alert(@PrimaryKey val ID: Int,val code: String, val errorDescription: String)


@Entity
data class Machine(@PrimaryKey val ID: Int)