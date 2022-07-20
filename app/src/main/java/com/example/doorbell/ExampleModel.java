package com.example.doorbell;

public class ExampleModel {

    public String ldr_id;
    public String sensor_id;
    public String device_id;
    public String ldr_value;
    public String time;

    public ExampleModel() {
    }

    public ExampleModel(String ldr_id, String sensor_id, String device_id, String ldr_value, String time) {
        this.ldr_id = ldr_id;
        this.sensor_id = sensor_id;
        this.device_id = device_id;
        this.ldr_value = ldr_value;
        this.time = time;
    }

    public String getLdr_id() {
        return ldr_id;
    }

    public void setLdr_id(String ldr_id) {
        this.ldr_id = ldr_id;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getLdr_value() {
        return ldr_value;
    }

    public void setLdr_value(String ldr_value) {
        this.ldr_value = ldr_value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
