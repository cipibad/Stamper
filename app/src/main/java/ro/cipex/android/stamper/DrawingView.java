package ro.cipex.android.stamper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {
    private static final String TAG = "DrawingView";

    private Shape mCurrentShape;
    private ShapeList mShapes;

    private int mShapeColor;
    private Paint mBackgroundPaint;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mShapes = ShapeList.get();

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xffffffff);
    }

    public int getShapeColor() {
        return mShapeColor;
    }

    public void setShapeColor(int c) {
        mShapeColor = c;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentShape = new Shape(current, mShapeColor);
                mShapes.add(mCurrentShape);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentShape != null) {
                    mCurrentShape.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurrentShape = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                mCurrentShape = null;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (Shape shape : mShapes) {
            shape.drawOn(canvas);
        }
    }
}
