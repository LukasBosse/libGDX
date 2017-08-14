package eon.ebs.entities;

import java.io.Serializable;
import java.util.Date;

public class Budget implements Serializable {

    private Date date;
    private int budget = 0;

    public Budget(int budget) {
        this.date = new Date();
        this.budget = budget;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
