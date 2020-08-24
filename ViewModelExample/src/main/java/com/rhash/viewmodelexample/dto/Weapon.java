package com.rhash.viewmodelexample.dto;

import java.util.Objects;

/**
 *
 * @author rhash
 */
public class Weapon {

    private String name;
    private int weight;
    private boolean isBeautiful;
    private int attackPower;
    private boolean isMagical;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isIsBeautiful() {
        return isBeautiful;
    }

    public void setIsBeautiful(boolean isBeautiful) {
        this.isBeautiful = isBeautiful;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public boolean isIsMagical() {
        return isMagical;
    }

    public void setIsMagical(boolean isMagical) {
        this.isMagical = isMagical;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + this.weight;
        hash = 19 * hash + (this.isBeautiful ? 1 : 0);
        hash = 19 * hash + this.attackPower;
        hash = 19 * hash + (this.isMagical ? 1 : 0);
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
        final Weapon other = (Weapon) obj;
        if (this.weight != other.weight) {
            return false;
        }
        if (this.isBeautiful != other.isBeautiful) {
            return false;
        }
        if (this.attackPower != other.attackPower) {
            return false;
        }
        if (this.isMagical != other.isMagical) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Weapon{" + "name=" + name + ", weight=" + weight + ", isBeautiful=" + isBeautiful + ", attackPower=" + attackPower + ", isMagical=" + isMagical + '}';
    }
    
    
}
