package ro.cipex.android.stamper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

/**
 * Created by cbadescu on 6/22/17.
 */

public class Shape {
    private static final String TAG = "Shape";
    private PointF mOrigin;
    private PointF mCurrent;

    public Shape(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
        Log.d(TAG, "New Shape, origin" + mOrigin.toString());
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
        Log.d(TAG, "New Shape, set current" + mCurrent.toString());
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public void drawOn(Canvas canvas, Paint shapePaint) {
        float left = Math.min(getOrigin().x, getCurrent().x);
        float right = Math.max(getOrigin().x, getCurrent().x);
        float top = Math.min(getOrigin().y, getCurrent().y);
        float bottom = Math.max(getOrigin().y, getCurrent().y);

        canvas.drawRect(left, top, right, bottom, shapePaint);

    }
}
