package com.github.hfantin.authenticationservice.repository

import com.github.hfantin.authenticationservice.model.UserOrganization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrgUserRepository : JpaRepository<UserOrganization, String> {
    fun findByUserName(userName: String): UserOrganization
}