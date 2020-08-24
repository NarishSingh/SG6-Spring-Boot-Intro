/*
Use a convert() method to change a regular model dto to a view model dto
VM's are better for display in Web API's
 */
package com.rhash.viewmodelexample.service;

import com.rhash.viewmodelexample.dao.WeaponDao;
import com.rhash.viewmodelexample.dto.Weapon;
import com.rhash.viewmodelexample.dto.WeaponVM;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rhash
 */
@Service
public class WeaponService {

    @Autowired
    WeaponDao weaponDao;

    // //////////////////////////////////////////////////
    // Regular Model Methods
    public List<Weapon> GetAllWeapons() {
        return weaponDao.GetAllWeapons();
    }

    public Weapon GetWeaponByName(String name) {
        return weaponDao.GetWeaponByName(name);
    }

    public Weapon AddWeapon(Weapon weapon) {
        return weaponDao.AddWeapon(weapon);
    }

    public Weapon EditWeapon(Weapon weapon) {
        return weaponDao.EditWeapon(weapon);
    }

    public void RemoveWeaponByName(String name) {
        weaponDao.DeteleWeaponByName(name);
    }

    // //////////////////////////////////////////////////
    // VIEW Model Methods
    /**
     * Convert all weapons to VM obj's
     *
     * @return {List} all weapons as VM's
     */
    public List<WeaponVM> getAllWeaponVM() {
        List<Weapon> weapons = weaponDao.GetAllWeapons();
        List<WeaponVM> weaponVMs = new ArrayList<>();

        for (Weapon weapon : weapons) {
            weaponVMs.add(convert(weapon));
        }

        return weaponVMs;
    }

    /**
     * Convert model to view model
     *
     * @param weapon {Weapon} regular Model obj
     * @return {WeaponVM} a VM obj
     */
    private WeaponVM convert(Weapon weapon) {
        WeaponVM weaponVM = new WeaponVM();

        weaponVM.setName(weapon.getName());
        weaponVM.setWeight(weapon.getWeight() + " lbs");
        weaponVM.setAttackPower(weapon.getAttackPower() + " power");

        //decription rendering as string based on boolean and weight fields
        String weaponDescription = "";

        if (weapon.isIsBeautiful()) {
            weaponDescription += "NICE ";
        } else {
            weaponDescription += "UGLY ";
        }

        if (weapon.getWeight() < 10) {
            weaponDescription += "LIGHT ";
        } else if (weapon.getWeight() < 50) {
            weaponDescription += "MEDIUM ";
        } else {
            weaponDescription += "HEAVY ";
        }

        if (weapon.isIsMagical()) {
            weaponDescription += "MAGIC ";
        } else {
            weaponDescription += "PHYSICAL ";
        }

        weaponDescription += "WEAPON";

        weaponVM.setDescription(weaponDescription);

        return weaponVM;
    }
}
