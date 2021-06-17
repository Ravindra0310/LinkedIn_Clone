package com.example.jobedin.RecyclerViewComponant;

public class Personality {

    private int imageId;
    private int circularImgId;
    private String name;

    private String profession;
    private String experience;

    public Personality(int imageId,int circularImgId ,String name, String profession, String experience) {
        this.imageId = imageId;
        this.name = name;
        this.circularImgId=circularImgId;
        this.profession = profession;
        this.experience = experience;

    }



    public int getCircularImgId() {
        return circularImgId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public String getExperience() {
        return experience;
    }
}
