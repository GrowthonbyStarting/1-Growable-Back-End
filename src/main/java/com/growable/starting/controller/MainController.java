package com.growable.starting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String main() {

        return "index"; // 메인 페이지 (React의 TSX 파일) 반환
    }

}
