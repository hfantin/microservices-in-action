package com.github.hfantin.organizationservice.services

import com.github.hfantin.organizationservice.events.models.OrganizationChangeModel
import com.github.hfantin.organizationservice.events.source.ChangeOrganizationProducer
import com.github.hfantin.organizationservice.events.source.ChangeOrganizationSource
import com.github.hfantin.organizationservice.model.Action
import com.github.hfantin.organizationservice.model.Organization
import com.github.hfantin.organizationservice.model.OrganizationChangeDto
import com.github.hfantin.organizationservice.repository.OrganizationRepository
import com.github.hfantin.organizationservice.util.UserContext
import com.github.hfantin.organizationservice.util.UserContextHolder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrganizationService {

    private val logger = LoggerFactory.getLogger(OrganizationService::class.java)

    @Autowired
    private lateinit var organizationRepository: OrganizationRepository

    @Autowired
    private lateinit var organizationSource: ChangeOrganizationSource

    @Autowired
    private lateinit var changeOrganizationProducer: ChangeOrganizationProducer

    fun get(organizationId: String) = organizationRepository.findById(organizationId)

    fun save(organization: Organization) {
        logger.info("saving organizanion $organization")
        organization.id = UUID.randomUUID().toString()
        organizationRepository.save(organization)
        sendOrganizationChange(Action.SAVE, organization.id)
    }


    fun update(organization: Organization) {
        organizationRepository.save(organization)
        sendOrganizationChange(Action.UPDATE, organization.id)
    }

    fun delete(organizationId: String) {
        organizationRepository.deleteById(organizationId)
        sendOrganizationChange(Action.DELETE, organizationId)
    }

    private fun sendOrganizationChange(action: Action, organizationId: String) {
        val change = OrganizationChangeModel(
                OrganizationChangeModel::class.java.typeName,
                action.toString(),
                organizationId,
                UserContextHolder.getContext().correlationId
        )
        changeOrganizationProducer.send(change, organizationSource)
    }

}
