package com.hack.easyhomeloan.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hack.easyhomeloan.R;
import com.hack.easyhomeloan.utilities.AppUtils;

import java.text.DecimalFormat;


@SuppressLint("NewApi")
public class EmiCircleDisplay extends View implements OnGestureListener {

    /**
     * paint used for drawing the text
     */
    public static final int PAINT_TEXT = 1;
    /**
     * paint representing the value bar
     */
    public static final int PAINT_ARC = 2;
    /**
     * paint representing the inner (by default white) area
     */
    public static final int PAINT_INNER = 3;
    private static final String LOG_TAG = "CircleDisplay";
    /**
     * The M text paint.
     */
    public Paint mTextPaint, /**
     * The M second text paint.
     */
    mSecondTextPaint;
    /**
     * The From module.
     */
    public String fromModule;
    /**
     * the unit that is represented by the circle-display
     */
    private String mUnit = "%";
    /**
     * startangle of the view
     */
    private float mStartAngle = 270f;
    /**
     * field representing the minimum selectable value in the display - the
     * minimum interval
     */
    private float mStepSize = 1f;
    /**
     * angle that represents the displayed value
     */
    private float mAngle = 0f;
    /**
     * current state of the animation
     */
    private float mPhase = 0f;
    /**
     * the currently displayed value, can be percent or actual value
     */
    private float mValue = 0f;
    /**
     * the maximum displayable value, depends on the set value
     */
    private float mMaxValue = 0f;
    /**
     * percent of the maximum width the arc takes
     */
    private float mValueWidthPercent = 2f;
    private float mInnerCircleRadius = 0f;
    /**
     * if enabled, the inner circle is drawn
     */
    private boolean mDrawInner = true;
    /**
     * if enabled, the center text is drawn
     */
    private boolean mDrawText = true;
    /**
     * if enabled, touching and therefore selecting values is enabled
     */
    private boolean mTouchEnabled = true;

    private int mArcPaintColor, mArcCirclePaintColor, mArcInnerCirclePaintColor;
    private int mArcTextPaintColor = Color.BLACK, mArcSecondaryTextPaintColor = Color.WHITE;
    private int mArcPaintTextSize = 8;
    private int mArcSecondaryPaintTextSize = 16;
    /**
     * represents the alpha value used for the remainder bar
     */
    private int mDimAlpha = 80;
    /**
     * the decimalformat responsible for formatting the values in the view
     */
//    private DecimalFormat mFormatValue = new DecimalFormat("###,###,###,##0.0");
    /**
     * array that contains values for the custom-text
     */
    private String[] mCustomText = null;
    /**
     * rect object that represents the bounds of the view, needed for drawing
     * the circle
     */
    private RectF mCircleBox = new RectF();
    private Paint mArcPaint, mArcCirclePaint;
    private Paint mInnerCirclePaint;
    /**
     * object animator for doing the drawing animations
     */
    private ObjectAnimator mDrawAnimator;
    /**
     * boolean flag that indicates if the box has been setup
     */
    private boolean mBoxSetup = false;
    /**
     * listener called when a value has been selected on touch
     */
    private SelectionListener mListener;
    private OnClickListener onClickListener;
    /**
     * gesturedetector for recognizing single-taps
     */
    private GestureDetector mGestureDetector;

    /**
     * Instantiates a new Circle display.
     *
     * @param context the context
     */
    public EmiCircleDisplay(Context context) {
        super(context);
        init();
    }

    /**
     * Instantiates a new Circle display.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public EmiCircleDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Instantiates a new Circle display.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public EmiCircleDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleDisplay, defStyleAttr, 0);
        mArcPaintTextSize = a.getDimensionPixelSize(R.styleable.CircleDisplay_arc_paintTextSize, 8);
        mArcSecondaryPaintTextSize = a.getDimensionPixelSize(R.styleable.CircleDisplay_arc_secondaryPaintTextSize, 16);

        mArcCirclePaintColor = a.getColor(R.styleable.CircleDisplay_arc_circlePaintColor, Color.RED);
        mArcPaintColor = a.getColor(R.styleable.CircleDisplay_arc_paintColor, Color.BLUE);
        mArcInnerCirclePaintColor = a.getColor(R.styleable.CircleDisplay_arc_innerCirclePaintColor, Color.GREEN);
        mArcTextPaintColor = a.getColor(R.styleable.CircleDisplay_arc_textPaintColor, Color.BLACK);
        mArcSecondaryTextPaintColor = a.getColor(R.styleable.CircleDisplay_arc_secondaryTextPaintColor, Color.WHITE);

        init();
    }

    private void init() {

        mBoxSetup = false;

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Style.FILL);
        mArcPaint.setStrokeWidth(30f);
        mArcPaint.setColor(mArcPaintColor);

        mArcCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcCirclePaint.setStyle(Style.FILL);
        mArcCirclePaint.setColor(mArcCirclePaintColor);


        mInnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCirclePaint.setStyle(Style.FILL);
        mInnerCirclePaint.setColor(mArcInnerCirclePaintColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Style.STROKE);
        mTextPaint.setTextAlign(Align.CENTER);
        mTextPaint.setColor(mArcTextPaintColor);
        mTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), mArcPaintTextSize));

        mSecondTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondTextPaint.setStyle(Style.FILL_AND_STROKE);
        mSecondTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mSecondTextPaint.setTextAlign(Align.CENTER);
        mSecondTextPaint.setColor(mArcSecondaryTextPaintColor);
        mSecondTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), mArcSecondaryPaintTextSize));
        mDrawAnimator = ObjectAnimator.ofFloat(this, "phase", mPhase, 1.0f).setDuration(3000);
        mDrawAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        mGestureDetector = new GestureDetector(getContext(), this);
    }

    /**
     * Sets secondary text color and size.
     *
     * @param color    the color
     * @param fontSize the font size
     */
    public void setSecondaryTextColorAndSize(int color, float fontSize) {
        mSecondTextPaint.setColor(color);
        mSecondTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), fontSize));
    }

    /**
     * Sets text paint text color and size.
     *
     * @param color    the color
     * @param fontSize the font size
     */
    public void setTextPaintTextColorAndSize(int color, float fontSize) {
        mTextPaint.setColor(color);
        mTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), fontSize));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mBoxSetup) {
            mBoxSetup = true;
            setupBox();
        }

        drawWholeCircle(canvas);

        drawValue(canvas);

        if (mDrawInner)
            drawInnerCircle(canvas);

        if (mDrawText) {

            if (mCustomText != null)
                drawCustomText(canvas);
            else
                drawText(canvas);
        }
    }

    /**
     * draws the custom text in the center of the view
     *
     * @param c
     */
    private void drawCustomText(Canvas c) {

        int index = (int) ((mValue * mPhase) / mStepSize);

        if (index < mCustomText.length) {
            c.drawText(mCustomText[index], getWidth() / 2,
                    getHeight() / 2 + mTextPaint.descent(), mTextPaint);
        } else {
            AppUtils.showLog(LOG_TAG, "Custom text array not long enough.");
        }
    }

    /**
     * draws the text in the center of the view
     *
     * @param c
     */
    private void drawText(Canvas c) {
        /*c.drawText(mFormatValue.format(mValue * mPhase) + " " + mUnit, getWidth() / 2,
                getHeight() / 2 + mTextPaint.descent(), mTextPaint);*/
        //munit="ALL LEAD
        //max value=58

        c.drawText((int) mMaxValue + mUnit, ((getWidth() / 2) + (mUnit.length() / 2) - 5), ((getHeight() / 2) + Utils.convertDpToPixel(getResources(), -5)) + mTextPaint.descent(), mSecondTextPaint);
//        c.drawText(mUnit, getWidth() / 2, ((getHeight() / 2) + Utils.convertDpToPixel(getResources(), 20)) + mTextPaint.descent(), mTextPaint);
    }

    /**
     * draws the background circle with less alpha
     *
     * @param c
     */
    private void drawWholeCircle(Canvas c) {
        mArcPaint.setAlpha(mDimAlpha);

        float r = getRadius();
        c.drawCircle(getWidth() / 2, getHeight() / 2, r, mArcCirclePaint);
        c.drawCircle(getWidth() / 2, getHeight() / 2, r, mArcPaint);
    }

    /**
     * draws the inner circle of the view
     *
     * @param c
     */
    private void drawInnerCircle(Canvas c) {

        if (mInnerCircleRadius == 0f)
            c.drawCircle(getWidth() / 2, getHeight() / 2, getRadius() / 100f * (100f - mValueWidthPercent), mInnerCirclePaint);
        else
            c.drawCircle(getWidth() / 2, getHeight() / 2, getRadius() - mInnerCircleRadius, mInnerCirclePaint);
    }

    /**
     * draws the actual value slice/arc
     *
     * @param c
     */
    private void drawValue(Canvas c) {

        mArcPaint.setAlpha(255);

        float angle = mAngle * mPhase;
        c.drawArc(mCircleBox, 0, 360, true, mArcCirclePaint);
        c.drawArc(mCircleBox, mStartAngle, angle, true, mArcPaint);


        // Log.i(LOG_TAG, "CircleBox bounds: " + mCircleBox.toString() +
        // ", Angle: " + angle + ", StartAngle: " + mStartAngle);
    }

    /**
     * sets up the bounds of the view
     */
    private void setupBox() {

        int width = getWidth();
        int height = getHeight();

        float diameter = getDiameter();

        mCircleBox = new RectF(width / 2 - diameter / 2, height / 2 - diameter / 2, width / 2
                + diameter / 2, height / 2 + diameter / 2);
    }

    /**
     * shows the given value in the circle view
     *
     * @param toShow   the to show
     * @param total    the total
     * @param animated the animated
     */
    public void showValue(float toShow, float total, boolean animated) {

        mAngle = calcAngle(toShow / total * 100f);
        mValue = toShow;
        mMaxValue = total;

        if (animated)
            startAnim();
        else {
            mPhase = 1f;
            invalidate();
        }
    }

    /**
     * Show value percentage.
     *
     * @param toShow   the to show
     * @param total    the total
     * @param animated the animated
     */
    public void showValuePercentage(float toShow, float total, boolean animated) {

        if (total <= 0 || toShow <= 0) {
            return;
        }
        try {
            DecimalFormat dec = new DecimalFormat("###");
            mAngle = calcAngle(toShow / total * 100f);
            mValue = toShow;
            mMaxValue = Float.parseFloat(dec.format((toShow / total) * 100));
            //mMaxValue = total;

            if (animated)
                startAnim();
            else {
                mPhase = 1f;
                invalidate();
            }
        } catch (Exception e) {
            AppUtils.showException(e);
        }
    }

    /**
     * Sets the unit that is displayed next to the value in the center of the
     * view. Default "%". Could be "€" or "$" or left blank or whatever it is
     * you display.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        mUnit = unit;
    }

    /**
     * Returns the currently displayed value from the view. Depending on the
     * used method to show the value, this value can be percent or actual value.
     *
     * @return value
     */
    public float getValue() {
        return mValue;
    }

    /**
     * Start anim.
     */
    public void startAnim() {
        mPhase = 0f;
        mDrawAnimator.start();
    }

    /**
     * set the duration of the drawing animation in milliseconds
     *
     * @param durationmillis the durationmillis
     */
    public void setAnimDuration(int durationmillis) {
        mDrawAnimator.setDuration(durationmillis);
    }

    /**
     * returns the diameter of the drawn circle/arc
     *
     * @return diameter
     */
    public float getDiameter() {
        return Math.min(getWidth(), getHeight());
    }

    /**
     * returns the radius of the drawn circle
     *
     * @return radius
     */
    public float getRadius() {
        return getDiameter() / 2f;
    }

    /**
     * calculates the needed angle for a given value
     *
     * @param percent
     * @return
     */
    private float calcAngle(float percent) {
        return percent / 100f * 360f;
    }

    /**
     * set the starting angle for the view
     *
     * @param angle the angle
     */
    public void setStartAngle(float angle) {
        mStartAngle = angle;
    }

    /**
     * returns the current animation status of the view
     *
     * @return phase
     */
    public float getPhase() {
        return mPhase;
    }

    /**
     * DONT USE THIS METHOD
     *
     * @param phase the phase
     */
    public void setPhase(float phase) {
        mPhase = phase;
        invalidate();
    }

    /**
     * set this to true to draw the inner circle, default: true
     *
     * @param enabled the enabled
     */
    public void setDrawInnerCircle(boolean enabled) {
        mDrawInner = enabled;
    }

    /**
     * returns true if drawing the inner circle is enabled, false if not
     *
     * @return boolean
     */
    public boolean isDrawInnerCircleEnabled() {
        return mDrawInner;
    }

    /**
     * set the drawing of the center text to be enabled or not
     *
     * @param enabled the enabled
     */
    public void setDrawText(boolean enabled) {
        mDrawText = enabled;
    }

    /**
     * returns true if drawing the text in the center is enabled
     *
     * @return boolean
     */
    public boolean isDrawTextEnabled() {
        return mDrawText;
    }

    /**
     * set the color of the arc
     *
     * @param color the color
     */
    public void setColor(int color) {
        mArcPaint.setColor(color);
    }

    /**
     * Sets secondary color.
     *
     * @param color the color
     */
    public void setSecondaryColor(int color) {
        mArcCirclePaint.setColor(color);
    }

    /**
     * set the size of the center text in dp
     *
     * @param size the size
     */
    public void setTextSize(float size) {
        mTextPaint.setTextSize(Utils.convertDpToPixel(getResources(), size));
    }

    /**
     * set the thickness of the value bar, default 50%
     *
     * @param percentFromTotalWidth the percent from total width
     */
    public void setValueWidthPercent(float percentFromTotalWidth) {
        mValueWidthPercent = percentFromTotalWidth;
    }

    /**
     * Set an array of custom texts to be drawn instead of the value in the
     * center of the CircleDisplay. If set to null, the custom text will be
     * reset and the value will be drawn. Make sure the length of the array corresponds with the maximum number of steps (set with setStepSize(float stepsize).
     *
     * @param custom the custom
     */
    public void setCustomText(String[] custom) {
        mCustomText = custom;
    }

    /**
     * sets the number of digits used to format values
     *
     * @param digits the digits
     */
    public void setFormatDigits(int digits) {

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            if (i == 0)
                b.append(".");
            b.append("0");
        }

//        mFormatValue = new DecimalFormat("###,###,###,##0" + b.toString());
    }

    /**
     * set the aplha value to be used for the remainder of the arc, default 80
     * (use value between 0 and 255)
     *
     * @param alpha the alpha
     */
    public void setDimAlpha(int alpha) {
        mDimAlpha = alpha;
    }

    /**
     * sets the given paint object to be used instead of the original/default
     * one
     *
     * @param which the which
     * @param p     the p
     */
    public void setPaint(int which, Paint p) {

        switch (which) {
            case PAINT_ARC:
                mArcPaint = p;
                break;
            case PAINT_INNER:
                mInnerCirclePaint = p;
                break;
            case PAINT_TEXT:
                mTextPaint = p;
                break;
        }
    }

    /**
     * returns the current stepsize of the display, default 1f
     *
     * @return step size
     */
    public float getStepSize() {
        return mStepSize;
    }

    /**
     * Sets the stepsize (minimum selection interval) of the circle display,
     * default 1f. It is recommended to make this value not higher than 1/5 of
     * the maximum selectable value, and not lower than 1/200 of the maximum
     * selectable value. For a maximum value of 100 for example, a stepsize
     * between 0.5 and 20 is recommended.
     *
     * @param stepsize the stepsize
     */
    public void setStepSize(float stepsize) {
        mStepSize = stepsize;
    }

    /**
     * returns the center point of the view in pixels
     *
     * @return center
     */
    public PointF getCenter() {
        return new PointF(getWidth() / 2, getHeight() / 2);
    }

    /**
     * returns true if touch-gestures are enabled, false if not
     *
     * @return boolean
     */
    public boolean isTouchEnabled() {
        return mTouchEnabled;
    }

    /**
     * Enable touch gestures on the circle-display. If enabled, selecting values
     * onTouch() is possible. Set a SelectionListener to retrieve selected
     * values. Do not forget to set a value before selecting values. By default
     * the maxvalue is 0f and therefore nothing can be selected.
     *
     * @param enabled the enabled
     */
    public void setTouchEnabled(boolean enabled) {
        mTouchEnabled = enabled;
    }

    /**
     * set a selection listener for the circle-display that is called whenever a
     * value is selected onTouch()
     *
     * @param l the l
     */
    public void setSelectionListener(SelectionListener l) {
        mListener = l;
    }

    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mTouchEnabled) {

            if (mListener == null)
                AppUtils.showLog(LOG_TAG,
                        "No SelectionListener specified. Use setSelectionListener(...) to set a listener for callbacks when selecting values.");

            if (onClickListener != null) {
                onClickListener.onClick(this);
            }

            // if the detector recognized a gesture, consume it
            if (mGestureDetector.onTouchEvent(e))
                return true;

            float x = e.getX();
            float y = e.getY();

            // get the distance from the touch to the center of the view
            float distance = distanceToCenter(x, y);
            float r = getRadius();

            // touch gestures only work when touches are made exactly on the
            // bar/arc
            if (distance >= r - r * mValueWidthPercent / 100f && distance < r) {

                switch (e.getAction()) {

                    // case MotionEvent.ACTION_DOWN:
                    // if (mListener != null)
                    // mListener.onSelectionStarted(mValue, mMaxValue);
                    // break;
                    case MotionEvent.ACTION_MOVE:

                        updateValue(x, y);
                        invalidate();
                        if (mListener != null)
                            mListener.onSelectionUpdate(mValue, mMaxValue);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mListener != null)
                            mListener.onValueSelected(mValue, mMaxValue);
                        break;
                }
            }

            return true;
        } else
            return super.onTouchEvent(e);
    }

    /**
     * updates the display with the given touch position, takes stepsize into
     * consideration
     *
     * @param x
     * @param y
     */
    private void updateValue(float x, float y) {

        // calculate the touch-angle
        float angle = getAngleForPoint(x, y);

        // calculate the new value depending on angle
        float newVal = mMaxValue * angle / 360f;

        // if no stepsize
        if (mStepSize == 0f) {
            mValue = newVal;
            mAngle = angle;
            return;
        }

        float remainder = newVal % mStepSize;

        // check if the new value is closer to the next, or the previous
        if (remainder <= mStepSize / 2f) {

            newVal = newVal - remainder;
        } else {
            newVal = newVal - remainder + mStepSize;
        }

        // set the new values
        mAngle = getAngleForValue(newVal);
        mValue = newVal;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        // get the distance from the touch to the center of the view
        float distance = distanceToCenter(e.getX(), e.getY());
        float r = getRadius();

        // touch gestures only work when touches are made exactly on the
        // bar/arc
        if (distance >= r - r * mValueWidthPercent / 100f && distance < r) {

            updateValue(e.getX(), e.getY());
            invalidate();

            if (mListener != null)
                mListener.onValueSelected(mValue, mMaxValue);
        }

        return true;
    }

    /**
     * returns the angle relative to the view center for the given point on the
     * chart in degrees. The angle is always between 0 and 360°, 0° is NORTH
     *
     * @param x the x
     * @param y the y
     * @return angle for point
     */
    public float getAngleForPoint(float x, float y) {

        PointF c = getCenter();

        double tx = x - c.x, ty = y - c.y;
        double length = Math.sqrt(tx * tx + ty * ty);
        double r = Math.acos(ty / length);

        float angle = (float) Math.toDegrees(r);

        if (x > c.x)
            angle = 360f - angle;

        angle = angle + 180;

        // neutralize overflow
        if (angle > 360f)
            angle = angle - 360f;

        return angle;
    }

    /**
     * returns the angle representing the given value
     *
     * @param value the value
     * @return angle for value
     */
    public float getAngleForValue(float value) {
        return value / mMaxValue * 360f;
    }

    /**
     * returns the value representing the given angle
     *
     * @param angle the angle
     * @return value for angle
     */
    public float getValueForAngle(float angle) {
        return angle / 360f * mMaxValue;
    }

    /**
     * returns the distance of a certain point on the view to the center of the
     * view
     *
     * @param x the x
     * @param y the y
     * @return float
     */
    public float distanceToCenter(float x, float y) {

        PointF c = getCenter();

        float dist = 0f;

        float xDist = 0f;
        float yDist = 0f;

        if (x > c.x) {
            xDist = x - c.x;
        } else {
            xDist = c.x - x;
        }

        if (y > c.y) {
            yDist = y - c.y;
        } else {
            yDist = c.y - y;
        }

        // pythagoras
        dist = (float) Math.sqrt(Math.pow(xDist, 2.0) + Math.pow(yDist, 2.0));

        return dist;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Sets text color.
     *
     * @param color the color
     */
    public void setTextColor(int color) {
        if (null != mTextPaint)
            mTextPaint.setColor(color);
    }

    /**
     * Sets inner circle color.
     *
     * @param color the color
     */
    public void setInnerCircleColor(int color) {
        if (null != mInnerCirclePaint)
            mInnerCirclePaint.setColor(color);
    }

    /**
     * Sets inner circle radius.
     *
     * @param radius the radius
     */
    public void setInnerCircleRadius(float radius) {
        this.mInnerCircleRadius = radius;
    }

    /**
     * listener for callbacks when selecting values ontouch
     *
     * @author Philipp Jahoda
     */
    public interface SelectionListener {

        /**
         * called everytime the user moves the finger on the circle-display
         *
         * @param val    the val
         * @param maxval the maxval
         */
        void onSelectionUpdate(float val, float maxval);

        /**
         * called when the user releases his finger fromt he circle-display
         *
         * @param val    the val
         * @param maxval the maxval
         */
        void onValueSelected(float val, float maxval);
    }

    /**
     * The type Utils.
     */
    public static abstract class Utils {

        /**
         * This method converts dp unit to equivalent pixels, depending on
         * device density.
         *
         * @param r  the r
         * @param dp A value in dp (density independent pixels) unit. Which we           need to convert into pixels
         * @return A float value to represent px equivalent to dp depending on device density
         */
        public static float convertDpToPixel(Resources r, float dp) {
            DisplayMetrics metrics = r.getDisplayMetrics();
            return (dp * (metrics.densityDpi / 160f));
        }
    }
}