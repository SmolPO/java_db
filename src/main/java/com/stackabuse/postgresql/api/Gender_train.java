package com.stackabuse.postgresql.api;
// interval	axis reading
public class Gender_train {
    private Integer customer_id;
    private Integer gender;

    public Gender_train(String[] data) {
        this.customer_id = Integer.parseInt(data[0]);
        this.gender = Integer.parseInt(data[1]);
    }
    public Gender_train(Integer customer_id, Integer gender) {
        this.customer_id = customer_id;
        this.gender = gender;
    }

    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Gender_test["
                + "customer_id=" + customer_id
                + ", gender=" + gender
                + ']';
    }
    public String to_csv() {
        String res = String.format("%d,%s\n",
                customer_id, gender);
        return res;
    }
}
