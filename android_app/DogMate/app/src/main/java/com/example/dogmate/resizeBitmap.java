package com.example.dogmate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.android.volley.VolleyLog.TAG;

public class resizeBitmap {

    public static Bitmap scaleBitmap(Bitmap initialBitmap, String imagePath, ImageView imageView) {

        Bitmap resizedBitmap = initialBitmap;

        try {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(imagePath);

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            int dstWidth = imageView.getWidth();
            int dstHeight = imageView.getHeight();

            // decode full image pre-resized
            in = new FileInputStream(imagePath);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth / dstWidth, inHeight / dstHeight);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth()
                    * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);

        } catch (IOException e) {
            Log.e("Image", e.getMessage(), e);
        }

        return resizedBitmap;
    }
}

