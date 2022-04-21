package com.stackabuse.postgresql.api;
// Таблица tr_mcc_codes.csv
// Данная таблица содержит описание mcc-кодов транзакций.
// Формат данных
// mcc_code;mcc_description
public class MCC_code {
    private Integer mcc_code;
    private String mcc_description;

    public MCC_code(Integer mcc_code, String mcc_description) {
        this.mcc_code = mcc_code;
        this.mcc_description = mcc_description;
    }
    public int getMcc_code() {
        return mcc_code;
    }
    public void setMcc_code(int mcc_code) {
        this.mcc_code = mcc_code;
    }

    public String getMcc_description() {
        return mcc_description;
    }
    public void setMcc_description(String mcc_code) {
        this.mcc_description = mcc_description;
    }

    @Override
    public String toString() {
        return "MCC_code["
                + "mcc_description=" + mcc_description
                + ']';
    }
}
