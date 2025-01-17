package com.event.transactions.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.event.transactions.model.Transaction;

@Database(entities = {Transaction.class}, version = 1)
public abstract class TransactionDB extends RoomDatabase {
    public abstract TransactionDao transactionDao();

    private static TransactionDB instance;

    public static synchronized TransactionDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                    TransactionDB.class, "TransactionDb")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(TransactionDB instance) {
            TransactionDao dao = instance.transactionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
