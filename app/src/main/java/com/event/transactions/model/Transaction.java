package com.event.transactions.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Transaction")
public class Transaction {
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("date")
    private String date;
    @SerializedName("category")
    private String category;
    @SerializedName("description")
    private String description;
    @SerializedName("amount")
    private String amount;

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }
}
