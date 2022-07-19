package com.funny.videos.api.controllers

import com.funny.videos.api.models.Conditions
import com.funny.videos.api.models.requests.SharedConditions
import com.funny.videos.api.models.requests.SharedVideoRequest
import com.funny.videos.api.models.responses.SharedVideoPagination
import com.funny.videos.api.services.JwtServices
import com.funny.videos.api.services.SharedVideoServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/videos")
class SharedVideoRestController {
    @Autowired
    private lateinit var service: SharedVideoServices

    @Autowired
    private lateinit var jwtServices: JwtServices

    @GetMapping("")
    fun findByConditions(
        @RequestParam(name = "page",  required = false, defaultValue = "0") page: Int = 0,
        @RequestParam(name = "size", required = false, defaultValue = "20") size: Int = 20,
        @RequestParam(name = "sort") sort: String?,
    ) = service.findSharedVideos(
        SharedConditions(page = page, size = size, sortBy = sort)
    )

    @PostMapping("/shared")
    fun shareVideos(
        @RequestBody request: SharedVideoRequest,
        @RequestParam(name = "page",  required = false, defaultValue = "0") page: Int = 0,
        @RequestParam(name = "size", required = false, defaultValue = "20") size: Int = 20,
        @RequestParam(name = "sort") sort: String?,
    ): SharedVideoPagination {
        val auth = jwtServices.find()
        request.validate()
        service.insert(request = request.toValidRequest(), auth = auth)
        return service.findSharedVideos(
            SharedConditions(page = page, size = size, sortBy = sort)
        )
    }
}