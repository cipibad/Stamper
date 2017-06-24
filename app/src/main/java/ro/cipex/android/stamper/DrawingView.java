package ro.cipex.android.stamper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class DrawingView extends View {
    private static final String TAG = "DrawingView";
    private Shape mCurrentShape;
    private List<Shape> mShapes = new ArrayList<>();
    private Paint mShapePaint;
    private Paint mBackgroundPaint;

    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mShapePaint = new Paint();
        mShapePaint.setColor(0x2200ff00);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xffeff8e0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurrentShape = new Shape(current);
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
            float left = Math.min(shape.getOrigin().x, shape.getCurrent().x);
            float right = Math.max(shape.getOrigin().x, shape.getCurrent().x);
            float top = Math.min(shape.getOrigin().y, shape.getCurrent().y);
            float bottom = Math.max(shape.getOrigin().y, shape.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mShapePaint);
        }
    }
}
