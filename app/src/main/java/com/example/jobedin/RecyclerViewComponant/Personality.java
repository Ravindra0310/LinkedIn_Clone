package com.example.jobedin.RecyclerViewComponant;

public class Personality {

    private String imageId;
    private int circularImgId;
    private String name;

    private String profession;
    private String experience;

    public Personality(String imageId,int circularImgId ,String name, String profession, String experience) {
        this.imageId = imageId;
        this.name = name;
        this.circularImgId=circularImgId;
        this.profession = profession;
        this.experience = experience;

    }



    public int getCircularImgId() {
        return circularImgId;
    }

    public String getImageId() {
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
