package com.speedcheck.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SPEED_TEST")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Timestamp timestamp;
    private boolean status = true;
    @Enumerated(EnumType.STRING)
    private TYPE type;
    private double speed;

    public Result() {

    }

    public Result(TYPE type, Timestamp timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public enum TYPE {
        DOWNLOAD, UPLOAD
    }
}
