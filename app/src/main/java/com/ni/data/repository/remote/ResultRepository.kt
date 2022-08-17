package com.ni.data.repository.remote

import com.ni.data.models.Result

interface ResultRepository {
    fun create(result: Result)
    fun retrieve(result: Result)
    fun update(result: Result)
    fun delete(result: Result)
}