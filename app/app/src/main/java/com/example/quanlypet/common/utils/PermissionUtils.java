package com.example.quanlypet.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    public static final Integer PERMISSION_REQUEST_CODE = 103;


    // Phương thức readExternalStorage(Activity context) kiểm tra quyền truy cập đọc và ghi bộ nhớ ngoài của thiết bị.
    // Nếu ứng dụng chưa được cấp quyền truy cập, phương thức sẽ yêu cầu người dùng cấp quyền truy cập bằng cách hiển thị một hộp thoại
    // yêu cầu quyền truy cập. Nếu người dùng từ chối cấp quyền truy cập, phương thức sẽ trả về giá trị false,
    // ngược lại sẽ trả về giá trị true
    public static boolean readExternalStorage(Activity context){
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);

            return false;
        }else {
            return true;
        }
    }

    ///hàm này được sử dụng để kiểm tra xem ứng dụng đã được cấp quyền gọi điện hay chưa. Nếu ứng dụng chưa được cấp quyền gọi điện,
    // hàm sẽ yêu cầu người dùng cấp quyền gọi điện và trả về giá trị false. Nếu người dùng chấp nhận yêu cầu, hàm
    // onRequestPermissionsResult sẽ được gọi để xử lý kết quả của yêu cầu. Nếu ứng dụng đã được cấp quyền gọi điện,
    // hàm sẽ trả về giá trị true
    public static boolean callPhone(Activity context){
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            return false;
        }else {
            return true;
        }
    }
}
