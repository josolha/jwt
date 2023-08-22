package com.example.jwt.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Log4j2
class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testGenerate(){
        Map<String, Object> claimMap = Map.of("mid","ABCDE");
        String jwtStr = jwtUtil.generateToken(claimMap,1);
        log.info(jwtStr);
    }

}