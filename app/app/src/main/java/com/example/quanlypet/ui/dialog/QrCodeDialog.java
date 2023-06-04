package com.example.quanlypet.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.example.quanlypet.R;
import com.example.quanlypet.model.Book;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

public class QrCodeDialog {
    private Activity activity;
    private Dialog dialog;
    private Toolbar toolbar;
    private ImageView ivQrCode;
    private Button btnBack;

    public QrCodeDialog(Activity activity, String title, Integer id){
        this.activity = activity;
        this.dialog = new Dialog(this.activity);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.setContentView(R.layout.dialog_qr_code);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        this.connectView(title, id);
    }

    private void connectView(String title, Integer id){
        toolbar = dialog.findViewById(R.id.toolbar);
        toolbar.setTitle("Mã QRCode: "+title);
        ivQrCode = dialog.findViewById(R.id.iv_qr_code);
        btnBack = dialog.findViewById(R.id.btn_back);

        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel> ();
        hintMap.put (EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix matrix = writer.encode(id.toString()
                    , BarcodeFormat.QR_CODE
                    , 550, 550, hintMap);
            int width   = matrix.getWidth();
            int height  = matrix.getHeight();
            Bitmap imageBitmap = Bitmap.createBitmap (width, height, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < width; i ++) {
                for (int j = 0; j < height; j ++) {
                    imageBitmap.setPixel (i, j, matrix.get (i, j) ? Color.BLACK: Color.WHITE);
                }
            }
            ivQrCode.setImageBitmap(imageBitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
    }

    public void show(){
        this.dialog.show();
    }

    public void hide(){
        this.dialog.dismiss();
    }
}
