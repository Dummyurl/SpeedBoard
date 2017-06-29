package com.anglll.speedboard;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yuan on 2017/6/28 0028.
 */

public class SpeedBoardView extends View {
    private Paint boardPaint;
    private Paint paint3;
    private Paint paint4;
    private int innerCircleRadius;
    private int pointerRadius = 15;
    private int pointer2Circle = 10;
    private int scaleLength = 60;
    private Paint pointerPaint;
    private int degree = 0;
    private ArgbEvaluator argbEvaluator;
    private String countString = "0";

    public SpeedBoardView(Context context) {
        this(context, null);
    }

    public SpeedBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeedBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        boardPaint = new Paint();
        boardPaint.setColor(Color.WHITE);
        boardPaint.setStyle(Paint.Style.FILL);
        boardPaint.setAntiAlias(true);
        boardPaint.setStrokeWidth(25);

        pointerPaint = new Paint(boardPaint);
        pointerPaint.setColor(Color.parseColor("#2371e0"));

        paint3 = new Paint(boardPaint);
        paint3.setColor(Color.RED);
        paint3.setStrokeWidth(4);
        paint3.setStyle(Paint.Style.STROKE);

        paint4 = new Paint(boardPaint);
        paint4.setColor(Color.BLACK);
        paint4.setTextAlign(Paint.Align.LEFT);
        argbEvaluator = new ArgbEvaluator();

        initSize();
    }

    private void initSize() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
////        canvas.translate(getWidth()/2,getHeight()/2);
////        canvas.drawArc(0,0,getWidth(),getHeight(),135,270,false, boardPaint);
////        canvas.drawPoint(((float) getWidth()/2),((float) getHeight()/2), boardPaint);
///*        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,boardPaint);
//        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,paint2);*/
//
////canvas.translate(getWidth()/2,getHeight()/2);
///*        RectF rect = new RectF(0,-400,400,0);
//        canvas.drawRect(rect,boardPaint);
//        canvas.scale(.5f,.5f,200,0);
//        canvas.drawRect(rect,paint2);*/
//
///*RectF rect = new RectF(-400,-400,400,400);
//        for(int i=0;i<=20;i++){
//            canvas.scale(.9f,.9f);
//            canvas.drawRect(rect,boardPaint);
//        }*/
///*        RectF rect = new RectF(0,-400,400,0);
//        canvas.drawRect(
//                rect,boardPaint
//        );
//        canvas.rotate(180);
//        canvas.rotate(20);
//        canvas.drawRect(rect,paint2);*/
//
////canvas.drawCircle(0,0,400,boardPaint);
////canvas.drawCircle(0,0,380,boardPaint);

        int width = getWidth();
        innerCircleRadius = getWidth() / 2 / 3;
        canvas.translate(width / 2, width / 2);

        canvas.drawCircle(0, 0, innerCircleRadius, paint3);
        canvas.drawCircle(0, 0, innerCircleRadius - 2, boardPaint);

        paint4.setTextSize(width / 7);
        Rect bounds = new Rect();
        paint4.getTextBounds(countString, 0, countString.length(), bounds);
        Paint.FontMetricsInt fontMetrics = paint4.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(countString, -bounds.width() / 2, bounds.height() / 2, paint4);

        float l = (float) ((width / 2 - 10) * Math.sqrt(2) / 2);
        float h = (float) ((width / 2 - 10 - scaleLength) * Math.sqrt(2) / 2);

        canvas.save();
        for (int i = 0; i <= 270; i += 10) {
            if (i <= degree) {
                boardPaint.setColor((int) argbEvaluator.evaluate(i / 270f, Color.GREEN, Color.RED));
            } else {
                boardPaint.setColor(Color.WHITE);
            }
            canvas.drawLine(-l, l, -h, h, boardPaint);
            canvas.rotate(10);
        }

        canvas.restore();

        canvas.save();
        canvas.rotate(degree - 135);
        canvas.drawArc(-pointerRadius, -(innerCircleRadius - pointer2Circle), pointerRadius, -(innerCircleRadius - pointer2Circle - 2 * pointerRadius), 0, 180, true, pointerPaint);
        Path p = new Path();
        p.moveTo(-pointerRadius, -(innerCircleRadius - pointer2Circle - pointerRadius));
        p.lineTo(pointerRadius, -(innerCircleRadius - pointer2Circle - pointerRadius));
        p.lineTo(0, -(width / 2 - 10 - 30 - scaleLength));
        p.close();
        canvas.drawPath(p, pointerPaint);
        canvas.restore();
    }

    public void setCount(int count, int max) {
        if (count > max) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(degree, count* 270 / max );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                degree = (int) valueAnimator.getAnimatedValue();
                countString = String.valueOf(degree);

                postInvalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }
}
