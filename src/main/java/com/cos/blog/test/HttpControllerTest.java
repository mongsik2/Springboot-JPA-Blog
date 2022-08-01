package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답 (HTML 파일)
// @Controller

// 사용자가 요청 => 응답(Data)
@RestController
public class HttpControllerTest {

    //http://localhost:9090/http/get
    @GetMapping("/http/get")
    public String getTest(Member m){ //http://localhost:9090/http/get?id=1&username=hi&password=1234&email=hi@gmail
        return "get 요청 : "+m.getId()+","+ m.getUsername()+ ","+ m.getPassword()+ ","+m.getEmail();
    }
    //http://localhost:9090/http/post
    @PostMapping("/http/post") //test/plain, application/json
    public String postTest(Member m){ //MessageConverter (스프링부트)
        return "post 요청 : "+m.getId()+","+ m.getUsername()+ ","+ m.getPassword()+ ","+m.getEmail();
    }
    //http://localhost:9090/http/put
    @PutMapping("/http/put")
    public String putTest(){
        return "put 요청";
    }
    //http://localhost:9090/http/delete
    @DeleteMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }
}
