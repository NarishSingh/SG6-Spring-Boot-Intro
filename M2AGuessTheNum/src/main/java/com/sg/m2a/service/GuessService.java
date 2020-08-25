package com.sg.m2a.service;

import com.sg.m2a.data.GameDao;
import com.sg.m2a.data.RoundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuessService {
    
    @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
}
