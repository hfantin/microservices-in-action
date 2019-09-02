package com.github.hfantin.licensingservice.model

class OrganizationChangeDto(
        val action: Action,
        val id: String
){
    override fun toString() = "{$action: $id}"
}

enum class Action {
    SAVE, UPDATE, DELETE
}
