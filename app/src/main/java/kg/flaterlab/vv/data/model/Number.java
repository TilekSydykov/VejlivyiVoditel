package kg.flaterlab.vv.data.model;

import java.io.Serializable;

public class Number implements Serializable {
    int id;
    String value;
    int votes;
    int minus;
    int plus;


    public Number() {
    }

    public Number(int id, String value, int votes, int minus, int plus) {
        this.id = id;
        this.value = value;
        this.votes = votes;
        this.minus = minus;
        this.plus = plus;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public int getVotes() {
        return votes;
    }

    public int getMinus() {
        return minus;
    }

    public int getPlus() {
        return plus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setMinus(int minus) {
        this.minus = minus;
    }

    public void setPlus(int plus) {
        this.plus = plus;
    }
}
