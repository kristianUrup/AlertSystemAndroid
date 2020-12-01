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
    val errorDescription: String,
    val isSubscribed: Boolean
)

@Entity
data class Machine(
    @PrimaryKey val machineId: String,
    val isSubscribed: Boolean
)

@Entity
data class MachineWatch(
    @PrimaryKey
    val machineId: String,
    val watchId: String
)

@Entity
data class AlarmWatch(
    @PrimaryKey
    val alarmId: Int,
    val watchId: String
)