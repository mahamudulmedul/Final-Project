package com.example.sazzad.farmersapp.Model;

import android.support.v7.widget.RecyclerView;

public class CropCultivation {
    private String cultivationId,cropName,place,time,process,image_url;

    public CropCultivation() {
    }

    public CropCultivation(String cultivationId, String cropName, String place, String time, String process, String image_url) {
        this.cultivationId = cultivationId;
        this.cropName = cropName;
        this.place = place;
        this.time = time;
        this.process = process;
        this.image_url=image_url;
    }

    public String getCultivationId() {
        return cultivationId;
    }

    public String getCropName() {
        return cropName;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public String getProcess() {
        return process;
    }
    public String getImage_url(){
        return image_url;
    }

    public void setCultivationId(String cultivationId) {
        this.cultivationId = cultivationId;
    }
}
