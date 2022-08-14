package com.ni.data.repository.remote

import com.ni.data.models.Booklet

interface LibraryRepository {
    fun createFile(booklet: Booklet)
    fun retrieveFile(booklet: Booklet)
    fun updateFile(booklet: Booklet)
    fun deleteFile(booklet: Booklet)
}