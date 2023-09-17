package com.example.jwt.controller;

import com.example.jwt.dto.PageRequestDTO;
import com.example.jwt.dto.PageResponseDTO;
import com.example.jwt.dto.TodoDTO;
import com.example.jwt.service.TodoService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO){

        log.info(todoDTO);

        Long tno = todoService.register(todoDTO);

        return Map.of("tno", tno);
    }

    @GetMapping("/{tno}")
    public TodoDTO read(@PathVariable("tno") Long tno){

        log.info("read tno: " + tno);

        return todoService.read(tno);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO){

        return todoService.list(pageRequestDTO);
    }

    @DeleteMapping(value= "/{tno}")
    public Map<String, String> delete(@PathVariable Long tno){

        todoService.remove(tno);
        return Map.of("result", "success");
    }

    @PutMapping(value="/{tno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> modify(@PathVariable("tno") Long tno,@RequestBody TodoDTO todoDTO ){

        //잘못된 tno가 발생하지 못하도록
        todoDTO.setTno(tno);

        todoService.modify(todoDTO);

        return Map.of("result", "success");
    }


}
