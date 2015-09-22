package com.galleriafrique.util.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by osifo on 9/20/15.
 */
public class BitmapHelper {
    public static Bitmap resizeBitmap(int width, int height, Bitmap originalImage) {

        float imageHeight = originalImage.getHeight();
        float imageWidth = originalImage.getWidth();
        if (imageHeight > 0 && imageWidth > 0) {
            Bitmap background = Bitmap.createBitmap((int) width, (int) height,
                    Bitmap.Config.ARGB_8888);
            float originalWidth = originalImage.getWidth(), originalHeight = originalImage
                    .getHeight();
            Canvas canvas = new Canvas(background);
            float scale = width / originalWidth;
            float xTranslation = 0.0f, yTranslation = (height - originalHeight
                    * scale) / 2.0f;
            Matrix transformation = new Matrix();
            transformation.postTranslate(xTranslation, yTranslation);
            transformation.preScale(scale, scale);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            canvas.drawBitmap(originalImage, transformation, paint);
            return background;
        } else {
            return originalImage;
        }
    }

    public static Bitmap getCircleImage(Bitmap src) {
        Bitmap source = Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight());
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();

        return bitmap;
    }
}
