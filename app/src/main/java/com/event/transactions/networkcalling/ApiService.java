package com.event.transactions.networkcalling;

import com.event.transactions.model.Transaction;
import com.event.transactions.request.LoginRequest;
import com.event.transactions.response.LoginResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("transactions")
    Call<List<Transaction>> transactions();
}

