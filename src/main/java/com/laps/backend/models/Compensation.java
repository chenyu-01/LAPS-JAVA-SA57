package com.laps.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name="compensation")
public class Compensation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private int days;
    private String status;

    public Compensation(){};
    public Compensation(String type,int days,String status){
        this.type = type;
        this.days = days;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Compensation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", days=" + days +
                ", status='" + status + '\'' +
                '}';
    }
}
