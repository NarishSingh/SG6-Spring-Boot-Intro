/*
Weapon View Model
*/
package com.rhash.viewmodelexample.dto;

import java.util.Objects;

/**
 *
 * @author naris
 */
public class WeaponVM {
    private String name;
    private String weight;
    private String attackPower;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(String attackPower) {
        this.attackPower = attackPower;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.weight);
        hash = 23 * hash + Objects.hashCode(this.attackPower);
        hash = 23 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WeaponVM other = (WeaponVM) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.weight, other.weight)) {
            return false;
        }
        if (!Objects.equals(this.attackPower, other.attackPower)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WeaponVM{" + "name=" + name + ", weight=" + weight + ", attackPower=" 
                + attackPower + ", description=" + description + '}';
    }
    
}
