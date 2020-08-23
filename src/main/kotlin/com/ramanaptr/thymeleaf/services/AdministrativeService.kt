package com.ramanaptr.thymeleaf.services

import com.ramanaptr.thymeleaf.model.entity.Administrative
import java.util.*


/**
 * ramanaptr 04-07-2020
 * */

interface AdministrativeService {

    fun findAll(): MutableList<Administrative>
    fun findById(id: Long): Optional<Administrative>
    fun findAllProvinces(): MutableList<Administrative>
    fun findAllDistricts(): MutableList<Administrative>
    fun findAllCity(id: Long): MutableList<Administrative>
    fun insertAllAdministrative()
    fun deletAll()
}