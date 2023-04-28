package com.example.edgeservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/defaultFallback")
    public String defaultFallBack(){
        return "Response from fallback";
    }
}

