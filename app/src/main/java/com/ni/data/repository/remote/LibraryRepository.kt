package com.ni.data.repository.remote

import com.ni.data.models.Booklet

interface LibraryRepository {
    fun getFile(booklet: Booklet)
    fun createFile(booklet: Booklet)
}