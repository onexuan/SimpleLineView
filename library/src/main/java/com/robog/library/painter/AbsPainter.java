package com.robog.library.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelPoint;

import java.util.List;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public abstract class AbsPainter implements Painter {

    private static final String TAG = "AbsPainter";

    protected static final int INTERVAL = 10;

    protected Chain chain;

    protected List<PixelPoint> pointList;

    protected boolean isStop;

    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public AbsPainter() {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.MITER);
    }

    @Override
    public void start(Chain chain, Action action) {

        this.chain = chain;

        pointList = action.fetchCoordinate(this);

        performDraw(action);

        chain.proceed();
    }

    @Override
    public void stop() {
        isStop = true;
    }

    @Override
    public void stick() {
        isStop = false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawPreviouse(canvas);
        realDraw(canvas);
    }

    protected abstract void realDraw(Canvas canvas);


    public void performDraw(Action action) {

    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    private void drawPreviouse(Canvas canvas) {
        if (chain != null) {

            int index = chain.index();

            final List<Painter> painters = chain.painters();

            for (int i = 0; i < index - 1; i ++) {
                final Painter painter = painters.get(i);
                painter.completeDraw(canvas);
            }


        }
    }

}