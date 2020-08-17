package com.sg.m2l7.simplestspringapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimpleController {

    @GetMapping
    public String[] helloWorld() {
        String[] result = {"Hello", "World", "!"};
        return result;
    }
    
    //from class, go to http://localhost:8080/api/homer
    @GetMapping("/homer")
    public String[] homerSimpson(){
        String[] result = {"DOH!"};
        return result;
    }

    @PostMapping("/calculate")
    public String calculate(int operand1, String operator, int operand2) {
        int result;

        switch (operator) {
            case "+": {
                result = operand1 + operand2;
                break;
            }
            case "-": {
                result = operand1 - operand2;
                break;
            }
            case "*": {
                result = operand1 * operand2;
                break;
            }
            case "/": {
                result = operand1 / operand2;
                break;
            }
            default: {
                String msg = String.format("operator '%s' is invalid", operator);
                throw new IllegalArgumentException(msg);
            }
        }

        return String.format("%s %s %s = %s", operand1, operator, operand2, result);
    }

    @DeleteMapping("/resource/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        //do nothing
    }
    
    //FIXME figure out how to do in postman
    @PostMapping("/addMoney/{boxId}/amount/{moneyAmount}")
    public String addMoney(@PathVariable int boxId, @PathVariable float moneyAmount){
        return String.format("$%s has been added to box %s", moneyAmount, boxId);
    }
    
}
