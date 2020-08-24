package com.rhash.viewmodelexample.dao;

import com.rhash.viewmodelexample.dto.Weapon;
import java.util.List;

/**
 *
 * @author rhash
 */
public interface WeaponDao {

    public List<Weapon> GetAllWeapons();

    public Weapon GetWeaponByName(String name);

    public Weapon AddWeapon(Weapon weapon);

    public Weapon EditWeapon(Weapon weapon);

    public void DeteleWeaponByName(String name);
}
