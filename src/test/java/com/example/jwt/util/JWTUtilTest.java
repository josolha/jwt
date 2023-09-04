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
    @Test
    public void testValidate() throws Exception{
        //given
        String jwtStr = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTM4MzU2NDYsIm1pZCI6IkFCQ0RFIiwiaWF0IjoxNjkzODM1NTg2fQ.b-71p5qaaE52TpfS4uVKGtkLzwN7c6VWbPygpG8Ot5c";

        //when
        Map<String,Object> claim = jwtUtil.validateToken(jwtStr);

        //then
        log.info(claim);
    }
    @Test
    public void testAll() throws Exception{

        //given
        String jwtStr = jwtUtil.generateToken(Map.of("mid","AAAA","email","aaaa@bbb.com"),1);

        log.info(jwtStr);

        Map<String, Object> claim = jwtUtil.validateToken(jwtStr);

        log.info("MID : "+ claim.get("mid"));
        log.info("EMAIL: "+claim.get("email"));

    }



}