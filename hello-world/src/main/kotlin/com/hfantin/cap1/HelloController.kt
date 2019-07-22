package com.hfantin.cap1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value="hello")
class HelloController {

    @RequestMapping(value=["/{firstName}/{lastName}","/{firstName}", ""], method=[RequestMethod.GET])
    fun hello(@PathVariable firstName: String?, @PathVariable lastName:String?) = "{message: \"Hello ${firstName?:""} ${lastName?:""}\"}"
}
