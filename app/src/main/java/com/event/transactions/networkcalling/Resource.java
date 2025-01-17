package com.event.transactions.networkcalling;

import static com.event.transactions.Constant.ERROR;
import static com.event.transactions.Constant.LOADING;
import static com.event.transactions.Constant.SUCCESS;

public class Resource<T> {
    public final int status;
    public final T data;
    public final String message;

    private Resource(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> loading(String message) {
        return new Resource<>(LOADING, null, null);
    }

    public static <T> Resource<T> success(T data, String message) {
        return new Resource<>(SUCCESS, data, message);
    }

    public static <T> Resource<T> error(String message) {
        return new Resource<>(ERROR, null, message);
    }
}
