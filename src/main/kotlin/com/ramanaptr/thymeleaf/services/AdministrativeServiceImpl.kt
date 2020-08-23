package com.ramanaptr.thymeleaf.services

import com.ramanaptr.thymeleaf.constant.Constant
import com.ramanaptr.thymeleaf.model.entity.Administrative
import com.ramanaptr.thymeleaf.repository.AdministrativeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * ramanaptr 04-07-2020
 * */

@Service
class AdministrativeServiceImpl : AdministrativeService {

    @Autowired
    private lateinit var administrativeRepository: AdministrativeRepository

    override fun findAll(): MutableList<Administrative> {
        return administrativeRepository.findAll()
    }

    override fun findById(id: Long): Optional<Administrative> {
        val administrative = administrativeRepository.findById(id)
        return administrative
    }

    override fun findAllProvinces(): MutableList<Administrative> {
        val provinces = administrativeRepository.findAllByType(Constant.PROVINCE)
        return provinces
    }

    override fun findAllDistricts(): MutableList<Administrative> {
        val districts = administrativeRepository.findAllByType(Constant.DISTRICT)
        return districts
    }

    override fun findAllCity(id: Long): MutableList<Administrative> {
        val districts = administrativeRepository.findById(id).orElse(null)

        val citys = administrativeRepository.findAllByType(Constant.CITY).filter { city ->
            val districtName = districts.name.replace("Kabupaten ", "").trim()
            val cityName = city.name.replace("Kota ", "").trim()
            districtName.contains(cityName, true)
        }.toMutableList()
        return citys
    }

    // Dummy Administrative
    override fun insertAllAdministrative() {
        val administrativeList = mutableListOf<Administrative>().apply {
            // Provinces
            add(Administrative(0, "Provinsi Bali", 1))

            // Districts
            add(Administrative(0, "Kabupaten Bangli", 2))
            add(Administrative(0, "Kabupaten Denpasar", 2))

            // City
            add(Administrative(0, "Kota Bangli", 3))
            add(Administrative(0, "Kota Denpasar", 3))
        }

        administrativeRepository.saveAll(administrativeList)
    }

    override fun deletAll() {
        administrativeRepository.deleteAll()
    }
}