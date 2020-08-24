package com.rhash.viewmodelexample.controller;

import com.rhash.viewmodelexample.dto.Weapon;
import com.rhash.viewmodelexample.service.WeaponService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author rhash
 */
@RestController
@RequestMapping("/weapon")
public class WeaponController {

    @Autowired
    WeaponService service;

    @GetMapping
    public List<Weapon> getAll() {
        return service.GetAllWeapons();
    }

    @GetMapping("/{name}")
    public Weapon getByName(@PathVariable String name) {
        return service.GetWeaponByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Weapon createWeapon(@RequestBody Weapon weapon) {
        return service.AddWeapon(weapon);
    }

    @PutMapping("/{name}")
    public Weapon updateWeapon(@PathVariable String name, @RequestBody Weapon weapon) {
        if (!name.equals(weapon.getName())) {
            service.RemoveWeaponByName(name);
        }
        return service.EditWeapon(weapon);
    }

    @DeleteMapping("{name}")
    public ResponseEntity deleteWeapon(@PathVariable String name) {
        service.RemoveWeaponByName(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
