package com.rhash.viewmodelexample.dao;

import com.rhash.viewmodelexample.dto.Weapon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rhash
 */
@Repository
public class WeaponMemoryDao implements WeaponDao {
    
    Map<String, Weapon> weapons = new HashMap();

    @Override
    public List<Weapon> GetAllWeapons() {
        return new ArrayList(weapons.values());
    }

    @Override
    public Weapon GetWeaponByName(String name) {
        return weapons.get(name);
    }

    @Override
    public Weapon AddWeapon(Weapon weapon) {
        weapons.put(weapon.getName(), weapon);
        return weapon;
    }

    @Override
    public Weapon EditWeapon(Weapon weapon) {
        weapons.put(weapon.getName(), weapon);
        return weapon;
    }

    @Override
    public void DeteleWeaponByName(String name) {
        weapons.remove(name);
    }
    
}
