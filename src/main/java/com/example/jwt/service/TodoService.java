package com.example.jwt.service;



import com.example.jwt.dto.PageRequestDTO;
import com.example.jwt.dto.PageResponseDTO;
import com.example.jwt.dto.TodoDTO;
import javax.transaction.Transactional;

@Transactional
public interface TodoService {

    Long register(TodoDTO todoDTO);

    TodoDTO read(Long tno);

    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);

    void remove(Long tno);

    void modify(TodoDTO todoDTO);

}