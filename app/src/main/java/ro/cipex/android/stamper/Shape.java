package ro.cipex.android.stamper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.Log;

/**
 * Created by cbadescu on 6/22/17.
 */


public class Shape {
    private static final String TAG = "Shape";
    private PointF mOrigin;
    private PointF mCurrent;
    int mColor;

    public Shape(PointF origin, int color) {
        mOrigin = origin;
        mCurrent = origin;
        mColor = color;
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

    int detectShape() {
        if (getOrigin().y < getCurrent().y) {
            if (getOrigin().x < getCurrent().x) {
                return ShapeType.CIRCLE;
            } else {
                return ShapeType.DIAMOND;
            }
        }
        else {
            if (getOrigin().x < getCurrent().x) {
                return ShapeType.TRIANGLE;
            }
        }
        return ShapeType.RECTANGLE;
    }

    public void drawOn(Canvas canvas) {
        float left = Math.min(getOrigin().x, getCurrent().x);
        float right = Math.max(getOrigin().x, getCurrent().x);
        float top = Math.min(getOrigin().y, getCurrent().y);
        float bottom = Math.max(getOrigin().y, getCurrent().y);

        Paint shapePaint = new Paint();
        shapePaint.setColor(mColor);
        shapePaint.setAlpha(0x33);


        switch (detectShape()) {
            case ShapeType.CIRCLE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    canvas.drawOval(left, top, right, bottom, shapePaint);
                }
                else {
                    float x = (right - left) / 2;
                    float y = (bottom - top) / 2;
                    float radius = Math.max((right - x), (bottom - y));
                    canvas.drawCircle(x, y, radius, shapePaint);
                }
                break;
            case ShapeType.TRIANGLE:
                drawTriangle(new PointF(left, bottom),
                        new PointF(right, bottom),
                        new PointF((right - left)/2 + left, top),
                        shapePaint, canvas);
                break;
            case ShapeType.DIAMOND:
                drawDiamond(new PointF((right - left)/2 + left, top),
                        new PointF(right, (bottom - top)/2 + top),
                        new PointF((right - left)/2 + left, bottom),
                        new PointF(left, (bottom - top)/2 + top),
                        shapePaint, canvas);
                break;
            default:
                canvas.drawRect(left, top, right, bottom, shapePaint);
        }
    }

    private void drawDiamond(PointF p1, PointF p2, PointF p3, PointF p4, Paint paint, Canvas canvas){
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.lineTo(p3.x,p3.y);
        path.lineTo(p4.x,p4.y);

        path.close();

        canvas.drawPath(path, paint);
    }

    private void drawTriangle(PointF p1, PointF p2, PointF p3, Paint paint, Canvas canvas){
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.lineTo(p3.x,p3.y);
        path.close();

        canvas.drawPath(path, paint);
    }
}
