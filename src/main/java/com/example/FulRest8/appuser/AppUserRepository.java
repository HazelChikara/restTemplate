package com.example.FulRest8.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@Transactional(readOnly = true)
//The type of id is long
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
//     to construct the actual query
     Optional<AppUser> findByEmail(String email);
}

