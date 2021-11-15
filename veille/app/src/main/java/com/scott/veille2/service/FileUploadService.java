package com.scott.veille2.service;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService {
    @Multipart
    @POST("/curriculum/upload")
    Call<ResponseBody> upload(
            @Part MultipartBody.Part file,
            @Part("id") long id
    );
}
