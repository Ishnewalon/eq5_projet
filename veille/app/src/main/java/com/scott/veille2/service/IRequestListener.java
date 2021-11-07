package com.scott.veille2.service;

public interface IRequestListener <T> {

    void processFinished(boolean isSuccessful, T response);
}
