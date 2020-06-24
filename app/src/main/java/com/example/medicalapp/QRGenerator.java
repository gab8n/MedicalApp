package com.example.medicalapp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRGenerator {
    public  static String data_stare;

  public static void GenerarareQR() {


      BitMatrix bitMatrix;
      ImageView QR_imageView = QRGeneretorFragment.sview.findViewById(R.id.QR_imageView);
      if(HomeActivity.user_stare.isEmpty()) {
          data_stare = "NECUNOSCUT";
      }else{
          data_stare = HomeActivity.user_stare;

      }

      MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

      {
          try {
              bitMatrix = multiFormatWriter.encode(data_stare, BarcodeFormat.QR_CODE, 200, 200);
              BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
              Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
              QR_imageView.setImageBitmap(bitmap);

          } catch (WriterException e) {
              e.printStackTrace();
          }
      }
  }




}
