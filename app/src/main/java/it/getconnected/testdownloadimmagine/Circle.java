package it.getconnected.testdownloadimmagine;

import android.graphics.PointF;

public class Circle {

    private static final String TAG = "MyCircle";
    private static int currentId = 0;
    private float mRadius = 100.0f;
    private int mId;
    private PointF mOrigin;

    public Circle() {
        mId = currentId++;
    }

    public Circle(PointF origin) {
        mOrigin = origin;
        mId = currentId++;
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

//    public boolean inCircle(PointF curr) {
//        return distanceSquared(curr) <= Math.pow(mRadius, 2);
//    }

//    private double distanceSquared(PointF p2) {
//        return Math.pow(mOrigin.x - p2.x, 2) + Math.pow(mOrigin.y - p2.y, 2);
//    }
}
