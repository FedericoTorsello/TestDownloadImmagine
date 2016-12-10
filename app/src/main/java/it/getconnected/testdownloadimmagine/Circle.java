package it.getconnected.testdownloadimmagine;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class Circle {

    private static final String TAG = "MyCircle";
    private static int currentId = 0;
    private float mRadius = 100.0f;
    private int mId;
    private PointF mOrigin;
    private Paint paint;

    public Circle() {
        this(new PointF());
    }

    public Circle(PointF origin) {
        mOrigin = origin;
        mId = currentId++;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setOrigin(PointF origin) {
        mOrigin = origin;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    //    public boolean inCircle(PointF curr) {
//        return distanceSquared(curr) <= Math.pow(mRadius, 2);
//    }

//    private double distanceSquared(PointF p2) {
//        return Math.pow(mOrigin.x - p2.x, 2) + Math.pow(mOrigin.y - p2.y, 2);
//    }
}
