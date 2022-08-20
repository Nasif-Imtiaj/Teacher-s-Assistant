package com.ni.data.repository.remote

import com.ni.data.models.Booklet

interface BookletRepository {
    fun createFile(booklet: Booklet)
    fun retrieveFile(userId:String,bookletCallBacks: BookletCallBacks)
    fun updateFile(booklet: Booklet)
    fun deleteFile(booklet: Booklet)
}

interface BookletCallBacks{
    fun onSuccess(list:ArrayList<Booklet>)
    fun onFailed()
}