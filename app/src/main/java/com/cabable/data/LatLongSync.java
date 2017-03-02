package com.cabable.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AllenMosesW on 22/01/17.
 */

public class LatLongSync {
    @SerializedName("_id")
    @Expose
    public Integer id;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("location")
    @Expose
    public List<Double> location = null;
    @SerializedName("city")
    @Expose
    public String city;


    public LatLongSync(int id, int satus, List<Double> latLong, String city) {
        this.id = id;
        this.status = satus;
        this.location = latLong;
        this.city = city;

    }
}
