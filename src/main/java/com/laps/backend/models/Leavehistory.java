package com.laps.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "leavehistory")
public class Leavehistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String start;
    private String end;
    private String type;
    private String reason;
    private String status;

    public Leavehistory(){}

    public Leavehistory(String start,String end,String type,
                        String reason, String status){
        this.start = start;
        this.end = end;
        this.type = type;
        this.reason = reason;
        this.status = status;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Leavehistory{" +
                "id=" + id +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", type='" + type + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
