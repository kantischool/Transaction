package com.event.transactions.room;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.event.transactions.model.Transaction;

import java.util.List;

@androidx.room.Dao
public interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(List<Transaction> transactions);

    @Query("SELECT * FROM `Transaction`")
    LiveData<List<Transaction>> transactions();
}
