package com.ramanaptr.thymeleaf.controller.thymeleaf

import com.ramanaptr.thymeleaf.model.entity.Visitor
import com.ramanaptr.thymeleaf.services.AdministrativeService
import com.ramanaptr.thymeleaf.services.VisitorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid

@Controller
class VisitorController {

    companion object {
        const val PATH_INDEX = "/"
        const val PATH_ADD_VISITOR = "/add"
        const val PATH_EDIT_VISITOR = "/edit"
        const val PATH_DELETE_VISITOR = "/delete"

        const val REDIRECT_ADD_VISITOR_HTML = "redirect:/add"

        const val INDEX_HTML = "index"
        const val ADD_VISITOR_HTML = "visitor"
    }

    @Autowired
    private lateinit var visitorService: VisitorService

    @Autowired
    private lateinit var administrativeService: AdministrativeService

    @GetMapping(PATH_INDEX)
    fun index(): String {
        return INDEX_HTML
    }

    @GetMapping(PATH_ADD_VISITOR)
    fun add(
            @ModelAttribute("visitor") visitor: Visitor,
            model: Model,
            redirectAttributes: RedirectAttributes
    ): String {

        showAdministrative(model)
        createGetModel(model, visitor)
        return ADD_VISITOR_HTML
    }

    private fun showAdministrative(model: Model) {
        if (administrativeService.findAllProvinces().size <= 0) {
            administrativeService.insertAllAdministrative()
        }

        val provinces = administrativeService.findAllProvinces()
        model.addAttribute("provinces", provinces)

    }

    @PostMapping(PATH_ADD_VISITOR)
    fun add(
            @Valid visitor: Visitor,
            bindingResult: BindingResult,
            redirectAttributes: RedirectAttributes,
            model: Model
    ): String {
        showAdministrative(model)

        // Bind the error on object is empty/blank
        if (bindingResult.hasFieldErrors()) {
            createGetModel(model, visitor)
            return ADD_VISITOR_HTML
        }

        // Flash on message attr if id equal less than zero and show save msg
        if (visitor.id <= 0) {
            redirectAttributes.addFlashAttribute("msg", "Visitor Saved!")
        }

        // Flash on message attr if id greater than zero and show update msg
        if (visitor.id > 0) {
            redirectAttributes.addFlashAttribute("msg", "Visitor Updated!")
        }

        // Save method
        redirectAttributes.addFlashAttribute("btnText", "Add Visitor")
        visitorService.insertOrUpdate(visitor)
        return REDIRECT_ADD_VISITOR_HTML
    }

    private fun createGetModel(model: Model, visitor: Visitor) {
        val visitors = visitorService.findAll()

        model.addAttribute("btnText", "Add Visitor")
        if (visitor.id > 0) {
            model.addAttribute("btnText", "Edit Visitor")
        }

        model.addAttribute("visitor", visitor)
        model.addAttribute("visitors", visitors)
    }

    @PostMapping(PATH_EDIT_VISITOR)
    fun edit(
            @RequestParam("id") id: String,
            redirectAttributes: RedirectAttributes
    ): String {
        val visitor = visitorService.findById(id.toLong()).orElse(Visitor())
        redirectAttributes.addFlashAttribute("msg", "Now you edit: " + visitor.name)
        redirectAttributes.addFlashAttribute("visitor", visitor)
        return REDIRECT_ADD_VISITOR_HTML
    }

    @PostMapping(PATH_DELETE_VISITOR)
    fun delete(
            @RequestParam("id") id: String
    ): String {
        visitorService.deleteById(id.toLong())
        return REDIRECT_ADD_VISITOR_HTML
    }

    // API FOR SELECTION OPTIONS
    @GetMapping("/district")
    fun getAdminDistrict(): ResponseEntity<Any> {
        val map = HashMap<String, Any>()
        val findAllCity = administrativeService.findAllDistricts()
        map["districts"] = findAllCity

        return ResponseEntity.ok<Any>(map)
    }


    @GetMapping("/city/{id}")
    fun getAdminCity(
            @PathVariable id: String
    ): ResponseEntity<Any> {
        val map = HashMap<String, Any>()
        val findAllCity = administrativeService.findAllCity(id.toLong())
        map["citys"] = findAllCity

        return ResponseEntity.ok<Any>(map)
    }

}