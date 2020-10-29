package com.espressif.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.espressif.rainmaker.R;


public class PaletteBar extends View {
    public static final String TAG = "PaletteBar";


    static final int RED = Color.rgb(255, 0, 0);
    static final int YELLOW = Color.rgb(255, 255, 0);
    static final int GREEN = Color.rgb(0, 255, 0);
    static final int TEAL = Color.rgb(128, 255, 255);
    static final int BLUE = Color.rgb(0, 0, 255);
    static final int VIOLET = Color.rgb(255, 0, 255);


    static int[] COLORS = {RED, YELLOW, GREEN, TEAL, BLUE, VIOLET, RED};
    private Paint rGBGradientPaint, backgroundPaint;
    private Shader gradient;


    private int mPaletteWidth = 0;
    private int mPaletteHeight = 0;

    /**
     * The border around the palette that indicates the selected color
     */
    private int mColorMargin = 10;


    private int mCurrentHueColor = 180;
    float hsv[] = new float[3];

    private PaletteBarListener mListener;
    private boolean sizeChanged;


    public PaletteBar(Context context) {
        this(context, null);
    }

    public PaletteBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaletteBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    /**
     * Set the width of the border that indicates the selected color, in pixels. The default value
     * is 10
     *
     * @param colorMarginPx The size of the margin in pixels
     */
    public void setColorMarginPx(int colorMarginPx) {
        mColorMargin = colorMarginPx;

        if (getContext() != null) {
            init(getContext());
            invalidate();
        }
    }

    public void setColor(int hue) {
        mCurrentHueColor = hue;
        invalidate();
    }


    public void init(Context context) {

        rGBGradientPaint = new Paint();

        backgroundPaint = new Paint();

        hsv[0] = mCurrentHueColor;
        hsv[1] = 10.0f;
        hsv[2] = 10.0f;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: mcurrentColor" + mCurrentHueColor);
        hsv[0] = mCurrentHueColor;
        int mCurrentIntColor = Color.HSVToColor(hsv);
        backgroundPaint.setColor(mCurrentIntColor);
        canvas.drawPaint(backgroundPaint);
        drawColorPalette(canvas);
    }


    private void drawColorPalette(Canvas canvas) {
        if (sizeChanged) {
            gradient = new LinearGradient(0 + mColorMargin, 0 + mColorMargin, mPaletteWidth - mColorMargin, 0 + mColorMargin, COLORS, null, Shader.TileMode.MIRROR);
            rGBGradientPaint.setShader(gradient);
            sizeChanged = false;
        }
        canvas.drawRect(0 + mColorMargin, 0 + mColorMargin, mPaletteWidth - mColorMargin, mPaletteHeight - mColorMargin, rGBGradientPaint);

    }

    /**
     * Get the current colour
     */
    public int getCurrentColor() {
        return mCurrentHueColor;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sizeChanged = true;
        mPaletteWidth = w;
        mPaletteHeight = h;
    }

    private final OnTouchListener mTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();

            float x = event.getX();
            float y = event.getY();

            if (x < mColorMargin)
                x = 0;
            if (x > mPaletteWidth - mColorMargin)
                x = mPaletteWidth;

            mCurrentHueColor = getColorFromCoords(x, y);
            if (mListener != null)
                mListener.onColorSelected(mCurrentHueColor);
            invalidate();
            return true;
        }
    };

    public int getColorFromCoords(float x, float y) {
        float percent = (x - mColorMargin) / (mPaletteWidth - (mColorMargin * 2)) * 100;
        float customHue = 360 * percent / 100;
        return Math.round(customHue);
    }

    public void setListener(PaletteBarListener listener) {
        mListener = listener;

        // We'll start listening for touches now that the implementer cares about them
        if (listener == null) {
            setOnTouchListener(null);
        } else {
            setOnTouchListener(mTouchListener);
        }
    }

    /**
     * Interface for receiving color selection in {@link PaletteBar}
     *
     * @author brianherbert
     */
    public interface PaletteBarListener {
        public void onColorSelected(int color);
    }


}
