package com.ramanaptr.thymeleaf.model.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


/**
 * ramanaptr 04-07-2020
 * */

@Entity
@Table(name = "visitor")
data class Visitor(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long = 0,

        @field:NotBlank(message = "Name cannot empty")
        var name: String = "",

        @field:NotBlank(message = "Age cannot empty")
        var age: String = "",

        @ManyToOne
        @field:NotNull(message = "Province cannot empty")
        var province: Administrative = Administrative(0, "", 0),

        @ManyToOne
        @field:NotNull(message = "District cannot empty")
        var district: Administrative = Administrative(0, "", 0),

        @ManyToOne
        @field:NotNull(message = "City cannot empty")
        var city: Administrative = Administrative(0, "", 0)
)