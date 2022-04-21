package com.stackabuse.postgresql.api;
// interval	axis reading
public class Accel {
    private Integer interval;
    private String axis;
    private float reading;
    public Accel(Integer interval, String axis, float reading) {
        this.interval = interval;
        this.axis = axis;
        this.reading = reading;
    }
    public int getInterval() {
        return interval;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }
    public String getAxis() {
        return axis;
    }
    public void setAxis(String axis) {
        this.axis = axis;
    }
    public float getReading() {
        return reading;
    }
    public void setReading(float reading) {
        this.reading = reading;
    }
    @Override
    public String toString() {
        return "Accel["
                + "interval=" + interval
                + ", axis=" + axis
                + ", reading=" + reading
                + ']';
    }
}
