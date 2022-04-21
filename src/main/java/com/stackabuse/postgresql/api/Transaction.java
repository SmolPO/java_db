package com.stackabuse.postgresql.api;
// Таблица transactions.csv
// customer_id,tr_datetime,mcc_code,tr_type,amount,term_id
public class Transaction {
    private Integer customer_id;
    private String tr_datetime;
    private Integer mcc_code;
    private Integer tr_type;
    private Integer amount;
    private Integer term_id;

    public Transaction(Integer customer_id,
                      String tr_datetime,
                      Integer mcc_code,
                      Integer tr_type,
                      Integer amount,
                      Integer term_id) {
        this.customer_id = customer_id;
        this.tr_datetime = tr_datetime;
        this.mcc_code = mcc_code;
        this.tr_type = tr_type;
        this.amount = amount;
        this.term_id = term_id;

    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getTr_datetime() {
        return tr_datetime;
    }
    public void setTr_datetime(String tr_datetime) {
        this.tr_datetime = tr_datetime;
    }

    public int getMcc_code() {
        return mcc_code;
    }
    public void setMcc_code(int mcc_code) {
        this.mcc_code = mcc_code;
    }

    public int getTr_type() {
        return tr_type;
    }
    public void setTr_type(int tr_type) {
        this.tr_type = tr_type;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTerm_id() {
        return term_id;
    }
    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    @Override
    public String toString() {
        return "Transacion["
                + "customer_id=" + customer_id
                + ", tr_datetime=" + tr_datetime
                + ", mcc_code=" + mcc_code
                + ", tr_type=" + tr_type
                + ", amount=" + amount
                + ", term_id=" + term_id
                + ']';
    }
}