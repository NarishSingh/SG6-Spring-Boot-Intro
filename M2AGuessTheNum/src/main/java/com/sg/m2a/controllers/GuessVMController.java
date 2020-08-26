package com.sg.m2a.controllers;

import com.sg.m2a.service.GuessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guessthenumber/vm")
public class GuessVMController {
    
    @Autowired
    GuessServiceImpl serv;
    
    
}
