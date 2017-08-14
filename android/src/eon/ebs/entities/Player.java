package eon.ebs.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String name;
    private int level;
    private int ep;
    private int stars;
    private int budget;
    private ArrayList<Budget> budgetList = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        level = 1;
        ep = 0;
        stars = 0;
        budget = 10000;
        pushToBudget(budget);
    }

    public void pushToBudget(int budget) { budgetList.add(new Budget(budget)); }

    public Budget getBudgetByIndex(int i) { return budgetList.get(i); }

    public ArrayList<Budget> getBudgetList() { return budgetList; }

    public int getBudget() { return budget; }

    public void setBudget(int budget) { this.budget = budget; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getEp() { return ep; }

    public void setEp(int ep) { this.ep = ep; }

    public int getStars() { return stars; }

    public void setStars(int stars) { this.stars = stars; }

}
