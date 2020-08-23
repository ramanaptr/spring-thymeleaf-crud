package com.ramanaptr.thymeleaf.services

import com.ramanaptr.thymeleaf.model.entity.Visitor
import java.util.*


/**
 * ramanaptr 04-07-2020
 * */

interface VisitorService {

    fun insertOrUpdate(visitor: Visitor): Visitor
    fun findAll() : List<Visitor>
    fun findById(id: Long): Optional<Visitor>
    fun deleteById(id: Long)
}