package com.ni.data.models

data class User(
    val id: String,
    var name: String,
    val userType: String,
    var imgUrl: String,
    var email: String,
    var linkdin: String,
    var phoneNumber: String,
) {
    constructor() : this("", "","", "", "", "", "")
}