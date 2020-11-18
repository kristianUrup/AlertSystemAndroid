package com.example.machinealertsubscription.BE

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Alarm(
    @PrimaryKey
    @SerializedName("alarmId")
    val id: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("description")
    val errorDescription: String)

@Entity
data class Machine(@PrimaryKey val machineId: String)