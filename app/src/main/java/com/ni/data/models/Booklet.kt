package com.ni.data.models

data class Booklet(
    val id: String,
    val userId: String,
    val name: String,
    val localFileUrl: String,
    val remoteFileUrl: String,
) {
    constructor() : this(
        "", "",
        "", "", ""
    )
}
