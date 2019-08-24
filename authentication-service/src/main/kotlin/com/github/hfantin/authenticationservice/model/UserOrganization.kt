package com.github.hfantin.authenticationservice.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_orgs")
data class UserOrganization(
        @Column(name = "organization_id", nullable = false)
        var organizationid: String = "",

        @Id
        @Column(name = "user_name", nullable = false)
        var userName: String = ""

) : Serializable
