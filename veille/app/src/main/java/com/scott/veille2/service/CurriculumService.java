package com.scott.veille2.service;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.scott.veille2.RequestSingleton;
import com.scott.veille2.model.Curriculum;
import com.scott.veille2.model.StudentCurriculumDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    public void getAllCvs(IRequestListener<StudentCurriculumDTO> listener, long studentId) {
        String url = API_URL + "/curriculum/all_student/" + studentId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    listener.processFinished(true, buildDto(response));
                },
                error -> {
                    Toast.makeText(context, "Imposible de chercher la liste des offres!",  Toast.LENGTH_SHORT).show();
                    listener.processFinished(false, null);
                });

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);
    }

    public void setPrincipal(IRequestListener<String> listener, long studentId, long cvId) {
        String url = API_URL + "/student/set_principal/" + studentId + "/" + cvId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    listener.processFinished(true, response.toString());
                },
                error -> {
                    listener.processFinished(false, error.getMessage());
                });

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);
    }

    private StudentCurriculumDTO buildDto(JSONObject response) {
        try {
            JSONObject principalObject = null;
            Curriculum principal = null;

            if (!response.isNull("principal")) {
                principalObject = response.getJSONObject("principal");
                principal = buildCurriculumFromJson(principalObject);
            }

            JSONArray curriculumJson = response.getJSONArray("curriculumList");
            List<Curriculum> curriculumList = new ArrayList<>();
            for (int j = 0; j< curriculumJson.length(); j++){
                curriculumList.add(buildCurriculumFromJson(curriculumJson.getJSONObject(j)));
            }

            return new StudentCurriculumDTO(principal, curriculumList);
        }catch (Exception e){
            return null;
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Curriculum buildCurriculumFromJson(JSONObject jsonObject) {
        try {
            return new Curriculum(
                    jsonObject.getLong("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("type"),
                    jsonObject.isNull("isValid") ? null : jsonObject.getBoolean("isValid")
            );
        } catch (JSONException e) {
            return null;
        }
    }

}
