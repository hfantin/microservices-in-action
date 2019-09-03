package com.github.hfantin.licensingservice.model

import java.io.Serializable

data class Organization(
        var id: String = "",
        var name: String = "",
        var contactName: String = "",
        var contactEmail: String = "",
        var contactPhone: String = ""
): Serializable