package ro.cipex.android.stamper;

import android.graphics.PointF;

/**
 * Created by cbadescu on 6/22/17.
 */

public class Shape {
    private PointF mOrigin;
    private PointF mCurrent;

    public Shape(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getOrigin() {
        return mOrigin;
    }
}
