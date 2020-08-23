package com.ramanaptr.thymeleaf.repository

import com.ramanaptr.thymeleaf.model.entity.Visitor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


/**
 * ramanaptr 04-07-2020
 * */

@Repository
interface VisitorRepository : JpaRepository<Visitor, Long> {
}