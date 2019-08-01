package com.github.hfantin.licensingservice.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "licenses")
data class License(
        @Id
        @Column(name = "license_id", nullable = false)
        var licenseId: String = "",

        @Column(name = "organization_id", nullable = false)
        var organizationId: String = "",

        @Column(name = "product_name", nullable = false)
        var productName: String = "",

        @Column(name = "license_type", nullable = false)
        var licenseType: String = "",

        @Column(name = "license_max", nullable = false)
        var licenseMax: Int = 0,

        @Column(name = "license_allocated", nullable = true)
        var licenseAllocated: Int? = null,

        @Transient
        var organizationName: String = "",

        @Transient
        var contactName: String = "",

        @Transient
        var contactPhone: String = "",

        @Transient
        var contactEmail: String = ""
)