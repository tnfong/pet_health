package com.example.quanlypet.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.quanlypet.common.response.CommonResponse;
import com.example.quanlypet.common.utils.RestUtils;
import com.example.quanlypet.model.Book;
import com.example.quanlypet.retrofit.BookRequest;
import com.example.quanlypet.retrofit.RetrofitRequest;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookRepository {
    private final String TAG = this.getClass().getName();
    private final BookRequest bookRequest;

    public BookRepository(){
        this.bookRequest = RetrofitRequest.instance().create(BookRequest.class);
    }

    public LiveData<CommonResponse<List<Book>>> list(Integer uid, Integer idStatus){
        MutableLiveData<CommonResponse<List<Book>>> data = new MutableLiveData<>();
        try{
            this.bookRequest.list(uid, idStatus).enqueue(new Callback<CommonResponse<List<Book>>>() {
                @Override
                public void onResponse(Call<CommonResponse<List<Book>>> call, Response<CommonResponse<List<Book>>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<List<Book>>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<Book>> info(Integer uid, Integer id){
        MutableLiveData<CommonResponse<Book>> data = new MutableLiveData<>();
        try{
            this.bookRequest.info(uid, id).enqueue(new Callback<CommonResponse<Book>>() {
                @Override
                public void onResponse(Call<CommonResponse<Book>> call, Response<CommonResponse<Book>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Book>> call, Throwable t) {
                    Log.e("UserRepository", "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public LiveData<CommonResponse<Book>> save(Book book){
        MultipartBody.Part multipart = null;
        RequestBody requestFile = null;

        if(book.getPhotoUri() != null && book.getPhotoUri().length() > 0){
            File file = new File(book.getPhotoUri());
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            multipart = MultipartBody.Part.createFormData("photo_file", file.getName(), requestFile);
        }else{
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            multipart = MultipartBody.Part.createFormData("photo_file", "", requestFile);
        }
        MutableLiveData<CommonResponse<Book>> data = new MutableLiveData<>();
        try{
            this.bookRequest.save(book.getIdUser(), book.getBodyId(), book.getBodyIdDoctor(), book.getBodyIdAnimal()
                            , book.getBodyIdStatus(), book.getBodyPhotoUrl(), book.getBodyTime(), book.getBodyTimeHold()
                            , book.getBodyDescription(), book.getBodyIdService(), multipart)
                    .enqueue(new Callback<CommonResponse<Book>>() {
                @Override
                public void onResponse(Call<CommonResponse<Book>> call, Response<CommonResponse<Book>> response) {
                    data.setValue(RestUtils.get(response));
                }

                @Override
                public void onFailure(Call<CommonResponse<Book>> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }

            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
