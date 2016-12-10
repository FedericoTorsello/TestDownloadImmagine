package it.getconnected.testdownloadimmagine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by federico on 08/12/16.
 */
public class BlueDotView extends SubsamplingScaleImageView {

    private List<Circle> circles;
    private Circle currentCircle;

    private Path path;

    private GestureDetector mGestureDetector;

    private boolean firstPathPoint = false;

    private boolean pro = false;

    private PointF vPoint;

    private Bitmap pin;

    private PointF sPin;

    private Paint paintPath;

    public BlueDotView(Context context) {
        super(context);

        initialise();

        vPoint = new PointF();

        circles = new ArrayList<>();

        paintPath = new Paint();
        paintPath.setAntiAlias(true);
        paintPath.setStrokeWidth(6f);
        paintPath.setColor(Color.GREEN);
        paintPath.setStyle(Paint.Style.STROKE);
        paintPath.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                PointF p = viewToSourceCoord(e.getX(), e.getY());
                if (currentCircle == null) {
                    currentCircle = new Circle();
                    currentCircle.setOrigin(p);
                    currentCircle.getPaint().setColor(getRandomColor());
                    circles.add(currentCircle);

                    if (!firstPathPoint) {
                        path.moveTo(e.getX(), e.getY());
                        firstPathPoint = true;
                    }
                } else {
                    circles.remove(currentCircle);
                }
            }
        });
    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void initialise() {
        setWillNotDraw(false);
        setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_INSIDE);

        float density = getResources().getDisplayMetrics().densityDpi;
        pin = BitmapFactory.decodeResource(getResources(), R.drawable.pushpin_blue);
        float w = (density / 420f) * pin.getWidth();
        float h = (density / 420f) * pin.getHeight();
        pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isReady()) {
            return;
        }

        drawPin(canvas);

        drawCircles(canvas);

        drawPath(canvas);

    }

    private void drawPin(Canvas canvas){
        if (sPin != null && pin != null) {
            PointF vPin = sourceToViewCoord(sPin);
            float vX = vPin.x - (pin.getWidth() / 2);
            float vY = vPin.y - pin.getHeight();
            canvas.drawBitmap(pin, vX, vY, null);
        }
    }

    private void drawCircles(Canvas canvas) {

        for (Circle circle : circles) {
            PointF p = sourceToViewCoord(circle.getOrigin());
            canvas.drawCircle(p.x, p.y, circle.getRadius() * getScale(), circle.getPaint());
        }
    }

    private void drawPath(Canvas canvas) {

//        PointF p = sourceToViewCoord(vPoint);

//        if (!firstPathPoint) {
//            path.moveTo(p.x, p.y);
//            firstPathPoint = true;
//        }

//        if (firstPathPoint) {
//            path.lineTo(p.x, p.y);
//        }

        canvas.drawPath(path, paintPath);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent e) {

        mGestureDetector.onTouchEvent(e);

        PointF p = viewToSourceCoord(new PointF(e.getX(), e.getY()));

        vPoint = p;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // set the circle position on the global variable
                currentCircle = getCircleAtPosition(p);

                if (!pro) {
                    sPin = p;
                    pro = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentCircle != null) {
                    currentCircle.getPaint().setStyle(Paint.Style.STROKE);
                    currentCircle.setOrigin(p);

                    path.lineTo(e.getX(), e.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentCircle != null) {
                    currentCircle.getPaint().setStyle(Paint.Style.FILL);
                }
                currentCircle = null;
                break;
        }



        invalidate();

        return super.onTouchEvent(e);
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
