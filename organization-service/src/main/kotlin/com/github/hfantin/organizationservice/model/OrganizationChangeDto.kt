package com.github.hfantin.organizationservice.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

class OrganizationChangeDto(
        val action: Action,
        val id: String
){
    override fun toString() = "{$action: $id}"
}

enum class Action {
    SAVE, UPDATE, DELETE
}
