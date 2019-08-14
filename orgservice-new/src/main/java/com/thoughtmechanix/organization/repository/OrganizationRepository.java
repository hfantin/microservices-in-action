package com.thoughtmechanix.organization.repository;

import com.thoughtmechanix.organization.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,String>{

}
