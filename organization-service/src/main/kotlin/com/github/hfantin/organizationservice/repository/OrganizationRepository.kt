package com.github.hfantin.organizationservice.repository

import com.github.hfantin.organizationservice.model.Organization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationRepository : JpaRepository<Organization, String>
