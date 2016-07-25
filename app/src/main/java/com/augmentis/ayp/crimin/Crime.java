package com.augmentis.ayp.crimin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Therdsak on 7/18/2016.
 */
public class Crime {
    private  UUID id;
    private String title;
    private Date crimeDate;
    private boolean solve;





    public Crime(){
        crimeDate = new Date();
        id = UUID.randomUUID();


    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCrimeDate() {
        return crimeDate;
    }

    public void setCrimeDate(Date crimeDate) {
        this.crimeDate = crimeDate;


    }

    public boolean isSolve() {
        return solve;
    }

    public void setSolve(boolean solve) {
        this.solve = solve;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UUID=").append(id);
        builder.append(",Title=").append(title);
        builder.append(", Crime Date=").append(crimeDate);
        builder.append(",Solved=").append(solve);

        return builder.toString() ;
    }
}
