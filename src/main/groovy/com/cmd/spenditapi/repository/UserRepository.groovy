package com.cmd.spenditapi.repository

import com.cmd.spenditapi.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findById(int id)
    boolean existsByEmail(String email)
    void deleteUserById(int id)
}
