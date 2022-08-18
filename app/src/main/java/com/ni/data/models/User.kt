package com.ni.data.models

data class User(
    val id: String,
    val userType: String,
    val imgUrl: String,
    val email: String,
    val linkdin: String,
    val phoneNumber: String,
) {
    constructor() : this("", "", "", "", "", "")
}