package com.github.hfantin.organizationservice.services

import com.github.hfantin.organizationservice.events.source.SimpleSourceBean
import com.github.hfantin.organizationservice.model.Organization
import com.github.hfantin.organizationservice.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrganizationService {

    @Autowired
    private lateinit var organizationRepository: OrganizationRepository

    @Autowired
    private lateinit var simpleSourceBean: SimpleSourceBean

    fun get(organizationId: String) = organizationRepository.findById(organizationId)

    fun save(organization: Organization) {
        organization.id = UUID.randomUUID().toString()
        organizationRepository.save(organization)
        simpleSourceBean.publishOrgChange("SAVE", organization.id)
    }

    fun update(organization: Organization) = organizationRepository.save(organization)

    fun delete(organizationId: String) = organizationRepository.deleteById(organizationId)

}
