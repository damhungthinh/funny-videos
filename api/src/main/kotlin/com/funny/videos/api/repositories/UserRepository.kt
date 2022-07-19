package com.funny.videos.api.repositories

import com.funny.videos.api.models.entities.User
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun getByUsername(@Param("username") username: String): User?
}