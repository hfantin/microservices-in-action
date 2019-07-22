package com.github.hfantin.licensingservice.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "licenses")
data class License(
       @Id
       @Column(name = "license_id", nullable = false)
       val licenseId: String= "",

       @Column(name = "organization_id", nullable = false)
       val organizationId: String= "",

       @Column(name = "product_name", nullable = false)
       val productName: String= "",

       @Column(name = "license_type", nullable = false)
       val licenseType: String = "",

       @Column(name = "license_max", nullable = false)
       val licenseMax: Int = 0,

       @Column(name = "license_allocated", nullable = true)
       val licenseAllocated: Int? = null
)