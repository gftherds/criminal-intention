package com.augmentis.ayp.crimin.model;

import android.graphics.Bitmap;

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
    private String suspect;



    public String getSusspect()
    {
        return suspect;
    }

    public void setSusspect(String susspect) {

        this.suspect = susspect;
    }

    public Crime(){
        this(UUID.randomUUID());


    }

    public Crime(UUID uuid){
        this.id = uuid;
        crimeDate = new Date();
    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getCrimeDate()

    {
        return crimeDate;
    }

    public void setCrimeDate(Date crimeDate) {
        this.crimeDate = crimeDate;


    }

    public boolean isSolve()

    {
        return solve;
    }

    public void setSolve(boolean solve)
    {
        this.solve = solve;
    }



    public String getPhotoFilename()
    {
        return "IMG_" + getId().toString()+ ".jpg";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UUID=").append(id);
        builder.append(",Title=").append(title);
        builder.append(",Crime Date=").append(crimeDate);
        builder.append(",Solved=").append(solve);
        builder.append(",Suspect=").append(suspect);
        return builder.toString() ;
    }
}
