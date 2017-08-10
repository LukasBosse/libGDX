package eon.ebs.entities;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;
    private int level;
    private int ep;
    private int stars;

    public Player(String name) {
        this.name = name;
        level = 1;
        ep = 0;
        stars = 0;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getEp() { return ep; }

    public void setEp(int ep) { this.ep = ep; }

    public int getStars() { return stars; }

    public void setStars(int stars) { this.stars = stars; }

}
