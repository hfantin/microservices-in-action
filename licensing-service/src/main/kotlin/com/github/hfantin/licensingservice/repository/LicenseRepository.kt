package com.github.hfantin.licensingservice.repository

import com.github.hfantin.licensingservice.model.License
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LicenseRepository : JpaRepository<License, String>{
    fun findByOrganizationId (organizationId: String) : MutableList<License>
    fun findByOrganizationIdAndLicenseId(organizationId: String, licenseId: String): License?
}