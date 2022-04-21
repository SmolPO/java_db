package com.stackabuse.postgresql.api;
// Таблица tr_types.csv
// Данная таблица содержит описание типов транзакций.
// Формат данных
// tr_type;tr_description
public class Tr_type {
    private Integer tr_type;
    private String tr_description;
    public Tr_type(String[] data) {
        this.tr_type = Integer.parseInt(data[0]);
        this.tr_description = data[1];
    }
    public Tr_type(Integer tr_type, String tr_description) {
        this.tr_type = tr_type;
        this.tr_description = tr_description;
    }
    public int getTr_type() {
        return tr_type;
    }
    public void setTr_type(int tr_type) {
        this.tr_type = tr_type;
    }

    public String getTr_description() {
        return tr_description;
    }
    public void setTr_description(String tr_description) {
        this.tr_description = tr_description;
    }

    @Override
    public String toString() {
        return "Tr_type["
                + "tr_type=" + tr_type
                + " tr_description=" + tr_description
                + ']';
    }
    public String to_csv() {
        String res = String.format("%d,%s\n",
                tr_type, tr_description);
        return res;
    }
}
