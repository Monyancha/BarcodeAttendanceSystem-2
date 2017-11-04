package com.example.d105.attendancesystem;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by D105 on 4/8/2017.
 */

public class Barcode_Activity extends Activity {
    SessionManager session;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barcode);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        email=pref.getString("E_MAIL", null); // getting String
        setAttendanceValues();

        // barcode data
        String barcode_data =email.toString()+"password:123456";

        // barcode image
        Bitmap bitmap = null;
        //ImageView iv = //new ImageView(this);iv_barcode
        ImageView iv = (ImageView) findViewById(R.id.iv_barcode);
        //iv.

        try {

            bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128, 600, 300);
            iv.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void setAttendanceValues() {
        Preference pre = Preference.getInstance(this);

        String subName = getIntent().getStringExtra("SubName");

        if (subName.equals("algorithm")) {
            Integer algoValue = pre.getInt("ALGORITHAM");

            if (algoValue < 100) {

                algoValue += 5;
                pre.putInt("ALGORITHAM", algoValue);
            }
        } else if (subName.equals("Computer network")) {

            Integer algoValue = pre.getInt("COMPNETWORK");
            if (algoValue < 100) {
                algoValue += 5;
                pre.putInt("COMPNETWORK", algoValue);
            }

        } else if (subName.equals("Database managment")) {

            Integer algoValue = pre.getInt("DBMGMT");

            if (algoValue < 100) {
                algoValue += 5;
                pre.putInt("DBMGMT", algoValue);
            }

        } else if (subName.equals("Mathematic")) {

            Integer algoValue = pre.getInt("MATH");

            if (algoValue < 100) {
                algoValue += 5;
                pre.putInt("MATH", algoValue);
            }

        }
    }

    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     *
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}

