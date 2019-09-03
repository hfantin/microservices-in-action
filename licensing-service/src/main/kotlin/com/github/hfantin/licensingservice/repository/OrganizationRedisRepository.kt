package com.github.hfantin.licensingservice.repository

import com.github.hfantin.licensingservice.model.Organization

interface OrganizationRedisRepository {

    fun saveOrganization(org: Organization)
    fun updateOrganization(org: Organization)
    fun deleteOrganization(organizationId: String)
    fun findOrganization(organizationId: String): Organization?
}