package com.example.jwt.repository;

import com.example.jwt.domain.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser,String> {
}
