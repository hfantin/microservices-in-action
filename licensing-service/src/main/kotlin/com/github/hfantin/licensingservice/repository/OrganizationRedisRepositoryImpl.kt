package com.github.hfantin.licensingservice.repository

import com.github.hfantin.licensingservice.model.Organization
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class OrganizationRedisRepositoryImpl: OrganizationRedisRepository {

    companion object{
        private val HASH_NAME = "organization"
    }

    private var logger = LoggerFactory.getLogger(javaClass.simpleName)

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    private lateinit var hashOperations: HashOperations<String, Any, Any>

    @PostConstruct
    fun init() {
        hashOperations = redisTemplate.opsForHash<Any, Any>()
    }

    override fun saveOrganization(org: Organization) {
        logger.info("saveOrganization ${org.id}")
        hashOperations.put(HASH_NAME, org.id, org)
    }

    override fun updateOrganization(org: Organization) {
        logger.info("updateOrganization ${org.id}")
        hashOperations.put(HASH_NAME, org.id, org)
    }

    override fun deleteOrganization(organizationId: String) {
        logger.info("deleteOrganization .$organizationId")
        hashOperations.delete(HASH_NAME, organizationId)
    }

    override fun findOrganization(organizationId: String): Organization? {
        logger.info("findOrganization $organizationId")
        return hashOperations.get(HASH_NAME, organizationId) as Organization?
    }
}