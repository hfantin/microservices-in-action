package com.github.hfantin.organizationservice.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "organizations")
data class Organization(
        @Id
        @Column(name = "organization_id", nullable = false)
        var id: String = "",

        @Column(name = "name", nullable = false)
        var name: String = "",

        @Column(name = "contact_name", nullable = false)
        var contactName: String = "",

        @Column(name = "contact_email", nullable = false)
        var contactEmail: String = "",

        @Column(name = "contact_phone", nullable = false)
        var contactPhone: String = ""

)
