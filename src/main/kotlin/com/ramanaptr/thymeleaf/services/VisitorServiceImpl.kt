package com.ramanaptr.thymeleaf.services

import com.ramanaptr.thymeleaf.model.entity.Visitor
import com.ramanaptr.thymeleaf.repository.VisitorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * ramanaptr 04-07-2020
 * */

@Service
class VisitorServiceImpl : VisitorService {

    @Autowired
    private lateinit var visitorRepository: VisitorRepository

    override fun insertOrUpdate(visitor: Visitor): Visitor {
        val save = visitorRepository.save(visitor)
        return save
    }

    override fun findAll(): List<Visitor> {
        return visitorRepository.findAll()
    }

    override fun deleteById(id: Long) {
        val visitor = findById(id).orElse(null)

        if (visitor == null) {
            println("Error cannot find visitor")
            return
        }

        visitorRepository.delete(visitor)
    }

    override fun findById(id: Long): Optional<Visitor> {
        val visitor = visitorRepository.findById(id)

        return visitor
    }
}