package com.stackabuse.postgresql.api;
// interval	axis reading
public class Gender_test {
    private Integer customer_id;

    public Gender_test(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "Gender_test["
                + "customer_id=" + customer_id
                + ']';
    }
}
