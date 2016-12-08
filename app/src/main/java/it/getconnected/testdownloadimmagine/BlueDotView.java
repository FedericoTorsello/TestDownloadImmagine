package it.getconnected.testdownloadimmagine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by federico on 08/12/16.
 */
public class BlueDotView extends SubsamplingScaleImageView {

    private List<Circle> circles;
    private Paint paint;
    private Circle currentCircle;

    private Path path;

    private GestureDetector mGestureDetector;

    private boolean firstPathPoint;

    PointF vPoint = new PointF();

    public BlueDotView(Context context) {
        super(context);

        circles = new ArrayList<>();

        path = new Path();
        firstPathPoint = true;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                if (currentCircle != null) {
                    circles.remove(currentCircle);
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isReady()) {
            return;
        }

        drawCircles(canvas);

//        drawPath(canvas);
    }

    private void drawCircles(Canvas canvas) {

        for (Circle circle : circles) {
            canvas.drawCircle(circle.getOrigin().x, circle.getOrigin().y, circle.getRadius(), paint);
        }
    }

    private void drawPath(Canvas canvas) {

        if (firstPathPoint) {
            path.moveTo(vPoint.x, vPoint.y);
            firstPathPoint = false;
        }

        path.lineTo(vPoint.x, vPoint.y);

        canvas.drawPath(path, paint);
    }

    boolean move;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent e) {

        mGestureDetector.onTouchEvent(e);

        PointF pointF = new PointF(e.getX(), e.getY());

        vPoint = pointF;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // set the circle position on the global variable
                currentCircle = getCircleAtPosition(pointF);
                if (currentCircle == null) {
                    currentCircle = new Circle(pointF);
                    circles.add(currentCircle);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentCircle == null) {
                    currentCircle = getCircleAtPosition(pointF);
                } else {
                    currentCircle.setOrigin(pointF);
                }
                break;
            case MotionEvent.ACTION_UP:
                currentCircle = null;
                break;
        }

        invalidate();

        return true;
    }

    private Circle getCircleAtPosition(PointF p) {

        for (Circle circle : circles) {
            if (p.x >= circle.getOrigin().x - circle.getRadius() &&
                    p.x <= circle.getOrigin().x + circle.getRadius() &&
                    p.y >= circle.getOrigin().y - circle.getRadius() &&
                    p.y <= circle.getOrigin().y + circle.getRadius()) {
                return circle;
            }
        }
        return null;
    }
}
