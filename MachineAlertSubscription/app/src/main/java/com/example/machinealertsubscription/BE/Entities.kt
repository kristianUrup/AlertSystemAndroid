package com.example.machinealertsubscription.BE

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alert(@PrimaryKey val ID: Int, val errorDescription: String, val machine: Machine)


@Entity
data class Machine(@PrimaryKey val ID: Int)