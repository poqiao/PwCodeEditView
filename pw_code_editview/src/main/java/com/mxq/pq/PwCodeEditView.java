package com.mxq.pq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


@SuppressLint("AppCompatCustomView")
public class PwCodeEditView extends EditText {
    private Paint mPaint;
    private int mBgColor;//背景颜色
    private int mCircleNumberColor;//圆点颜色
    private int mBorderColor;//边框颜色
    private int mUnderLineColor;//下划线颜色
    private int mBorderSize;//边框大小
    private int mUnderLineSize;//当type==1时线大小
    private int mCircleSize;//圆点大小
    private int mBorderCornerSize;//边框大小
    private int mUnderCorner;//下划线花椒
    private int mNumber;//密码数量  默认6个
    private int size;
    private RectF rectF;
    private int mWidth, mHeight;
    private int mBorderType;//
    private int mSpace;//当mType ==1时，每条线相隔多少距离
    private int mShowType;//0为圆，1为数字
    private int mNumberSize;//文字大小
    private int mUnderLineBottom;//下划线距离底部距离

    public PwCodeEditView(Context context) {
        super (context);
    }

    public PwCodeEditView(Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);
        initView (context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        size = dip2px (2);
        TypedArray array = context.obtainStyledAttributes (attrs, R.styleable.PwCodeEditView);
        mNumber = array.getInt (R.styleable.PwCodeEditView_pw_number, 6);//默认6个
        mBgColor = array.getColor (R.styleable.PwCodeEditView_pw_bg_color, Color.WHITE);//默认白色
        mCircleNumberColor = array.getColor (R.styleable.PwCodeEditView_pw_circle_number_color, Color.BLACK);//默认黑色
        mBorderColor = array.getColor (R.styleable.PwCodeEditView_pw_border_color, Color.BLACK);//默认黑色
        mCircleSize = array.getDimensionPixelOffset (R.styleable.PwCodeEditView_pw_circle_radius, dip2px (6));//默认6dp
        mBorderCornerSize = array.getDimensionPixelOffset (R.styleable.PwCodeEditView_pw_border_corner, 0);
        mBorderSize = array.getDimensionPixelOffset (R.styleable.PwCodeEditView_pw_border_size, size);//默认为1dp
        mUnderLineSize = array.getDimensionPixelSize (R.styleable.PwCodeEditView_pw_underline_size, size);
        mUnderCorner = array.getDimensionPixelOffset (R.styleable.PwCodeEditView_pw_underline_conrner, 0);
        mNumberSize = array.getDimensionPixelSize (R.styleable.PwCodeEditView_pw_number_size, sp2px (24));
        mBorderType = array.getInt (R.styleable.PwCodeEditView_pw_border_type, 0);//默认有边框
        mUnderLineColor = array.getColor (R.styleable.PwCodeEditView_pw_underline_color, Color.BLACK);//默认黑色
        mShowType = array.getInt (R.styleable.PwCodeEditView_pw_show_type, 1);//默认是圆
        mUnderLineBottom = array.getDimensionPixelOffset (R.styleable.PwCodeEditView_pw_underline_bottom, dip2px (1));
        array.recycle ();//释放资源

        mPaint = new Paint ();
        mPaint.setAntiAlias (true);
        mPaint.setDither (true);
        mPaint.setStrokeWidth (size);
        mSpace = dip2px (5);//下划线之间的间隔
        setFilters (new InputFilter[]{new InputFilter.LengthFilter (mNumber)});//限制字符，防止到最大数后还能输入
        setBackgroundColor (0);

        setInputType (EditorInfo.TYPE_CLASS_NUMBER);     // 输入模式是数字  
        setCursorVisible (false);      // 不显示光标
        setLongClickable (false);      //取消长按事件，禁止弹出粘贴复制全选等选项
        setTextIsSelectable (false);//取消长按事件，禁止弹出粘贴复制全选等选项
    }

    public PwCodeEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw (canvas);
        if (mBgColor != 0) {
            mPaint.setStyle (Paint.Style.FILL);
            mPaint.setColor (mBgColor);
            canvas.drawRoundRect (rectF, mBorderCornerSize, mBorderCornerSize, mPaint);//mBorderCornerSize默认为零的
        }
        if (mBorderType == 0) {//有边框
            mPaint.setStyle (Paint.Style.STROKE);
            mPaint.setColor (mBorderColor);
            mPaint.setStrokeWidth (mBorderSize);
            canvas.drawRoundRect (rectF, mBorderCornerSize, mBorderCornerSize, mPaint);
            //画竖线
            for (int i = 1; i < mNumber; i++) {
                canvas.drawLine (mWidth * i / mNumber, 0, mWidth * i / mNumber, mHeight, mPaint);
            }
        } else {//下划线
            mPaint.setColor (mUnderLineColor);
            for (int i = 0; i < mNumber; i++) {
                canvas.drawRoundRect (mWidth * i / mNumber + mSpace,
                        mHeight - mUnderLineSize - mUnderLineBottom,
                        mWidth * (i + 1) / mNumber - mSpace,
                        mHeight - mUnderLineBottom, mUnderCorner, mUnderCorner, mPaint);//是否要在最低，自己改，默认是线的一半

            }
        }


        mPaint.setStyle (Paint.Style.FILL);//实心
        mPaint.setColor (mCircleNumberColor);//设置圆的颜色

        if (mShowType == 1) {//画圆点

            for (int i = 0; i < getText ().length (); i++) {//获得输入的长度
                canvas.drawCircle (mWidth * (i * 2 + 1) / (mNumber * 2),
                        mBorderType == 0 ? mHeight / 2 : (mHeight - mUnderLineSize - mUnderLineBottom) / 2,
                        mBorderType == 0 ? getCircleSize (0) : getCircleSize (1), mPaint);
            }
        } else {//画数字
            Paint.FontMetrics font = mPaint.getFontMetrics ();//用来获取文字的正确位置
            float top = font.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = font.bottom;//为基线到字体下边框的距离,即上图中的bottom
            int baseLineY = (int) (rectF.centerY () - top / 2 - bottom / 2 - mUnderLineSize/2 -mUnderLineBottom);//基线中间点的y轴计算公式
            mPaint.setTextAlign (Paint.Align.CENTER);
            mPaint.setTextSize (mBorderType==0?getTvSize (0):getTvSize (1));
            for (int i = 0; i < getText ().length (); i++) {
                canvas.drawText (getText ().toString ().substring (i, i + 1), mWidth * (i * 2 + 1) / (mNumber * 2), baseLineY, mPaint);
            }
        }

    }

    private float getTvSize(int i) {
        if (i==0&&(mHeight-mBorderSize*2<mNumberSize))
        {
                mNumberSize = mHeight-mBorderSize*2;
        }
        else if (i==1&&(mHeight-mUnderLineBottom-mUnderLineSize<mNumberSize))
        {
            mNumberSize = mHeight-mUnderLineBottom-mUnderLineSize;
        }
        return mNumberSize;
    }

    /**
     * @param i 显示类型，0是边框，1是下划线
     */
    private float getCircleSize(int i) {

        if (i == 0 && mHeight - mBorderSize * 2 < (mCircleSize * 2)) {
            mCircleSize = mHeight / 2 - mBorderSize;
        } else if (i == 1 && (mHeight - mUnderLineSize - mUnderLineBottom < mCircleSize * 2)) {
            mCircleSize = (mHeight - mUnderLineSize - mUnderLineBottom) / 2;
        }
        return mCircleSize;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize (widthMeasureSpec);
        mHeight = mWidth / 6;//设置成每一个都是正方形
        setMeasuredDimension (widthMeasureSpec, mHeight);
        if (mBorderType == 0) {
            rectF = new RectF (mBorderSize / 2, mBorderSize / 2, mWidth - mBorderSize / 2, mHeight - mBorderSize / 2);//mBorderSize / 2是为了防止画出界,导致边框四边大小只有竖线的一半
        } else {
            rectF = new RectF (0, 0, mWidth, mHeight);

        }

    }

    /**
     * @param sp 转换大小
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_SP, sp, getResources ().getDisplayMetrics ());
    }

    /**
     * @param dip 转换大小
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP, dip, getResources ().getDisplayMetrics ());
    }

}
