package com.example.jobedin.RecyclerViewComponant.JobRecycelerView;

public class JobModel {
    private int imageId;
    private String jobType;
    private String companyName;
    private String jobLocation;
    private String jobPostTiming;

    public JobModel(int imageId, String jobType, String companyName, String jobLocation, String jobPostTiming) {
        this.imageId = imageId;
        this.jobType = jobType;
        this.companyName = companyName;
        this.jobLocation = jobLocation;
        this.jobPostTiming = jobPostTiming;
    }

    public int getImageId() {
        return imageId;
    }

    public String getJobType() {
        return jobType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public String getJobPostTiming() {
        return jobPostTiming;
    }
}
