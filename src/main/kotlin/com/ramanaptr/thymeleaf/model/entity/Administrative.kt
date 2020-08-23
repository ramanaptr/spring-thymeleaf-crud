package com.ramanaptr.thymeleaf.model.entity

import org.hibernate.validator.constraints.Range
import javax.persistence.*
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size


/**
 * ramanaptr 06-07-2020
 * */


/**
 * @type
 * 1 = provinces
 * 2 = districts
 * 3 = city
 * */

@Entity
@Table(name = "administrative")
data class Administrative(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
//        @Range(min = 1, message = "Please select another one")
        @Size(min=1, max=10)
        @Pattern(regexp="(^[0-9]{10})")
        var id: Long = 0,

        var name: String = "",

        var type: Int = 0
)