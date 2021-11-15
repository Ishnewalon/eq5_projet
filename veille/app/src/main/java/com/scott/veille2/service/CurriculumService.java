package com.scott.veille2.service;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurriculumService implements IServiceUtil {

    private final Context context;

    public CurriculumService (Context context) {
        this.context = context;
    }

    public void uploadFile(Uri fileUri, long studentID) {
        FileUploadService service = ServiceGenerator.createService(FileUploadService.class);

        File file = getFile(context, fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(PDF_TYPE), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Call<ResponseBody> call = service.upload(body, studentID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "Success");
                if (response.code() == 201){
                    Toast.makeText(context, "Curriculum televerser avec succes!",  Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Erreur!" + response.message(),  Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(context, "Erreur! Impossible de televerser le C.V.",  Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File getFile(Context context, Uri uri) {
        try (InputStream in = context.getContentResolver().openInputStream(uri)) {
            File root = new File(context.getDataDir().getPath() + "/cv");

            if (!root.exists())
                root.mkdir();

            File tmp = new File(root, "cv.pdf");

            try (OutputStream out = new FileOutputStream(tmp)) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                return tmp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
