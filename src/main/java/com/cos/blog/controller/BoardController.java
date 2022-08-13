package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    //@AuthenticationPrincipal PrincipalDetails principalDetails
    @GetMapping({"", "/"})
    public String index(){ // 컨트롤러에서 세션을 어떻게 찾을지
        return "index";
    }

    // USER 권한 필요
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

}
