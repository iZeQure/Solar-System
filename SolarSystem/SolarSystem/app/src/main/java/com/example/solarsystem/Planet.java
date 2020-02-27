package com.example.solarsystem;

import java.util.Observable;

public class Planet extends Observable {
    private String name;
    private Integer imageId;
    private String description;
    private Integer sound;
    private Boolean lightIsOn;

    public Planet(String name,Integer imageId,String description,Integer sound){
        setName(name);
        setImageId(imageId);
        setDescription(description);
        setSound(sound);
        setLightIsOn(false);
    }

    public Planet(String name,Integer imageId){
        setName(name);
        setImageId(imageId);
        setLightIsOn(false);
    }

    public Integer getSound() {
        return sound;
    }

    public void setSound(Integer sound) {
        this.sound = sound;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLightIsOn() {
        return lightIsOn;
    }

    public void setLightIsOn(Boolean lightIsOn) {
        this.lightIsOn = lightIsOn;
        setChanged();
        notifyObservers();
    }
}
