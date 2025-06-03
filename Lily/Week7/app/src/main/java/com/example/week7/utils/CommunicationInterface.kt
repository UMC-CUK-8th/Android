package com.example.week7.utils

import com.example.week7.data.entities.Album

interface CommunicationInterface {
    fun sendData(album: Album)
}