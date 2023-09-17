package com.example.jwt.repository.search;

import com.example.jwt.dto.PageRequestDTO;
import com.example.jwt.dto.TodoDTO;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<TodoDTO> list(PageRequestDTO pageRequestDTO);
}
