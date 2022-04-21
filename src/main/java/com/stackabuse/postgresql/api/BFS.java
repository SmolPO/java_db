package com.stackabuse.postgresql.api;
// interval	axis reading
public class BFS {
    private Integer customer_id;
    private float probability;
    public BFS(Integer customer_id, float probability) {
        this.customer_id = customer_id;
        this.probability = probability;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }
    public float getProbability() {
        return probability;
    }
    public void setAxis(float probability) {
        this.probability = probability;
    }
    @Override
    public String toString() {
        return "Accel["
                + "customer_id=" + customer_id
                + ", probability=" + probability
                + ']';
    }
}
