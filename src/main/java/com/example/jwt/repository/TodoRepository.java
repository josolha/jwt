package com.example.jwt.repository;


import com.example.jwt.domain.Todo;
import com.example.jwt.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}