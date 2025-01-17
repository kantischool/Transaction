package com.event.transactions.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.event.transactions.model.Transaction;
import com.event.transactions.networkcalling.ApiService;
import com.event.transactions.networkcalling.Resource;
import com.event.transactions.request.LoginRequest;
import com.event.transactions.response.LoginResponse;
import com.event.transactions.room.TransactionDB;
import com.event.transactions.room.TransactionDao;

import java.util.List;

import jakarta.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {
    ApiService apiService;
    Context context;
    TransactionDB transactionDb;
    TransactionDao transactionDao;

    @Inject
    public AppRepository(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
        transactionDb = TransactionDB.getInstance(context);
        transactionDao = transactionDb.transactionDao();
    }

    public LiveData<Resource<LoginResponse>> login(LoginRequest loginRequest) {
        MutableLiveData<Resource<LoginResponse>> result = new MutableLiveData<>();
        result.postValue(Resource.loading(null));

        apiService.login(loginRequest).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    result.postValue(Resource.success(response.body(), null));
                } else {
                    result.postValue(Resource.error(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable throwable) {
                result.postValue(Resource.error(throwable.getMessage()));
            }
        });
        return result;
    }

    public LiveData<Resource<List<Transaction>>> transaction() {
        MutableLiveData<Resource<List<Transaction>>> result = new MutableLiveData<>();
        result.postValue(Resource.loading(null));

        apiService.transactions().enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<List<Transaction>> call, @NonNull Response<List<Transaction>> response) {
                if (response.isSuccessful()) {
                    result.postValue(Resource.success(response.body(), null));
                    new InsertCourseAsyncTask(transactionDao).execute(response.body());
                } else {
                    result.postValue(Resource.error(response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Transaction>> call, @NonNull Throwable throwable) {
                result.postValue(Resource.error(throwable.getMessage()));
            }
        });
        return result;
    }

    public LiveData<List<Transaction>> getTransactionsFromRoom() {
        return transactionDao.transactions();
    }

    private static class InsertCourseAsyncTask extends AsyncTask<List<Transaction>, Void, Void> {
        private TransactionDao dao;

        private InsertCourseAsyncTask(TransactionDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<Transaction>... model) {
            // below line is use to insert our modal in dao.
            dao.insertTransaction(model[0]);
            return null;
        }
    }
}
