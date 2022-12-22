package uz.shahzod.unzosoft.iosui_android.ui.iosSwitch

import android.content.Context
import android.graphics.*
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import uz.shahzod.unzosoft.iosui_android.R


/**
 * Created by Abdurashidov Shahzod on 22/12/22 15:42.
 * company QQBank
 * shahzod9933@gmail.com
 */

class IosSwitch @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private val DEFAULT_COLOR_ON = -0xac2897
    private val DEFAULT_COLOR_OFF = -0x1c1c1d
    private var colorOn = DEFAULT_COLOR_ON
    private var colorOff = DEFAULT_COLOR_OFF
    private val paint = Paint()
    private val sPath = Path()
    private val bPath = Path()
    private val bRectF = RectF()
    private var sAnim = 0f
    private var bAnim = 0f
    private var shadowGradient: RadialGradient? = null
    private val aInterpolator = AccelerateInterpolator(2F)
    private var state = STATE_SWITCH_OFF
    private var lastState = state
    private var isChecked = false
    private var mWidth = 0
    private var mHeight = 0
    private var sWidth = 0f
    private var sHeight = 0f
    private var sLeft = 0f
    private var sTop = 0f
    private var sRight = 0f
    private var sBottom = 0f
    private var sCenterX = 0f
    private var sCenterY = 0f
    private var sScale = 0f
    private var bOffset = 0f
    private var bRadius = 0f
    private var bStrokeWidth = 0f
    private var bWidth = 0f
    private var bLeft = 0f
    private var bTop = 0f
    private var bRight = 0f
    private var bBottom = 0f
    private var bOnLeftX = 0f
    private var bOn2LeftX = 0f
    private var bOff2LeftX = 0f
    private var bOffLeftX = 0f
    private var shadowHeight = 0f
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    init {
        init(attrs)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private fun init(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.app)
        try {
            colorOn = a.getColor(R.styleable.app_color_on, DEFAULT_COLOR_ON)
            colorOff = a.getColor(R.styleable.app_color_off, DEFAULT_COLOR_OFF)
            isChecked = a.getBoolean(R.styleable.app_checked, false)
            state = if (isChecked) STATE_SWITCH_ON else STATE_SWITCH_OFF
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = (widthSize * 0.65f).toInt()
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        sTop = 0f
        sLeft = sTop
        sRight = mWidth.toFloat()
        sBottom = mHeight * 0.91f
        sWidth = sRight - sLeft
        sHeight = sBottom - sTop
        sCenterX = (sRight + sLeft) / 2
        sCenterY = (sBottom + sTop) / 2
        shadowHeight = mHeight - sBottom
        bTop = 0f
        bLeft = bTop
        bBottom = sBottom
        bRight = bBottom
        bWidth = bRight - bLeft
        val halfHeightOfS = (sBottom - sTop) / 2
        bRadius = halfHeightOfS * 0.95f
        bOffset = bRadius * 0.2f
        bStrokeWidth = (halfHeightOfS - bRadius) * 2
        bOnLeftX = sWidth - bWidth
        bOn2LeftX = bOnLeftX - bOffset
        bOffLeftX = 0f
        bOff2LeftX = 0f
        sScale = 1 - bStrokeWidth / sHeight
        val sRectF = RectF(sLeft, sTop, sBottom, sBottom)
        sPath.arcTo(sRectF, 90f, 180f)
        sRectF.left = sRight - sBottom
        sRectF.right = sRight
        sPath.arcTo(sRectF, 270f, 180f)
        sPath.close()
        bRectF.left = bLeft
        bRectF.right = bRight
        bRectF.top = bTop + bStrokeWidth / 2
        bRectF.bottom = bBottom - bStrokeWidth / 2
        shadowGradient = RadialGradient(
            bWidth / 2, bWidth / 2, bWidth / 2, -0x1000000, 0x00000000, Shader.TileMode.CLAMP
        )
    }

    private fun calcBPath(percent: Float) {
        bPath.reset()
        bRectF.left = bLeft + bStrokeWidth / 2
        bRectF.right = bRight - bStrokeWidth / 2
        bPath.arcTo(bRectF, 90f, 180f)
        bRectF.left = bLeft + percent * bOffset + bStrokeWidth / 2
        bRectF.right = bRight + percent * bOffset - bStrokeWidth / 2
        bPath.arcTo(bRectF, 270f, 180f)
        bPath.close()
    }

    private fun calcBTranslate(percent: Float): Float {
        var result = 0f
        when (state - lastState) {
            1 ->
                if (state == STATE_SWITCH_OFF2) {
                    result = bOff2LeftX - (bOff2LeftX - bOffLeftX) * percent
                } else if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOn2LeftX) * percent
                }
            2 ->
                if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOff2LeftX) * percent
                } else if (state == STATE_SWITCH_ON) {
                    result = bOn2LeftX - (bOn2LeftX - bOffLeftX) * percent
                }
            3 -> result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent
            -1 ->
                if (state == STATE_SWITCH_ON2) {
                    result = bOn2LeftX + (bOnLeftX - bOn2LeftX) * percent
                } else if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX + (bOff2LeftX - bOffLeftX) * percent
                }
            -2 ->
                if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX + (bOn2LeftX - bOffLeftX) * percent
                } else if (state == STATE_SWITCH_OFF2) {
                    result = bOff2LeftX + (bOnLeftX - bOff2LeftX) * percent
                }
            -3 -> result = bOffLeftX + (bOnLeftX - bOffLeftX) * percent
        }
        return result - bOffLeftX
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.isAntiAlias = true
        val isChecked = state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2
        // draw background
        paint.style = Paint.Style.FILL
        paint.color = if (isChecked) colorOn else colorOff
        canvas.drawPath(sPath, paint)
        sAnim = if (sAnim - 0.1f > 0) sAnim - 0.1f else 0F
        bAnim = if (bAnim - 0.1f > 0) bAnim - 0.1f else 0F
        val dsAnim = aInterpolator.getInterpolation(sAnim)
        val dbAnim = aInterpolator.getInterpolation(bAnim)
        // draw background animation
        val scale = sScale * if (isChecked) dsAnim else 1 - dsAnim
        val scaleOffset = (bOnLeftX + bRadius - sCenterX) * if (isChecked) 1 - dsAnim else dsAnim
        canvas.save()
        canvas.scale(scale, scale, sCenterX + scaleOffset, sCenterY)
        paint.color = -0x1
        canvas.drawPath(sPath, paint)
        canvas.restore()
        // draw center bar
        canvas.save()
        canvas.translate(calcBTranslate(dbAnim), shadowHeight)
        val isState2 = state == STATE_SWITCH_ON2 || state == STATE_SWITCH_OFF2
        calcBPath(if (isState2) 1 - dbAnim else dbAnim)
        // draw shadow
        paint.style = Paint.Style.FILL
        paint.color = -0xcccccd
        paint.shader = shadowGradient
        canvas.drawPath(bPath, paint)
        paint.shader = null
        canvas.translate(0f, -shadowHeight)
        canvas.scale(0.98f, 0.98f, bWidth / 2, bWidth / 2)
        paint.style = Paint.Style.FILL
        paint.color = -0x1
        canvas.drawPath(bPath, paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = bStrokeWidth * 0.5f
        paint.color = if (isChecked) colorOn else colorOff
        canvas.drawPath(bPath, paint)
        canvas.restore()
        paint.reset()
        if (sAnim > 0 || bAnim > 0) invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if ((state == STATE_SWITCH_ON || state == STATE_SWITCH_OFF) && sAnim * bAnim == 0f) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> return true
                MotionEvent.ACTION_UP -> {
                    lastState = state
                    if (state == STATE_SWITCH_OFF) {
                        refreshState(STATE_SWITCH_OFF2)
                    } else if (state == STATE_SWITCH_ON) {
                        refreshState(STATE_SWITCH_ON2)
                    }
                    bAnim = 1f
                    invalidate()
                    if (state == STATE_SWITCH_OFF2) {
                        toggle(STATE_SWITCH_ON)
                    } else if (state == STATE_SWITCH_ON2) {
                        toggle(STATE_SWITCH_OFF)
                    }
                    if (onCheckedChangeListener != null) {
                        onCheckedChangeListener!!.onCheckedChanged(this, isChecked)
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun refreshState(newState: Int) {
        if (!isChecked && newState == STATE_SWITCH_ON) {
            isChecked = true
        } else if (isChecked && newState == STATE_SWITCH_OFF) {
            isChecked = false
        }
        lastState = state
        state = newState
        postInvalidate()
    }

    fun isChecked(): Boolean {
        return isChecked
    }
    fun setChecked(isChecked: Boolean) {
        refreshState(if (isChecked) STATE_SWITCH_ON else STATE_SWITCH_OFF)
    }


    fun toggle(isChecked: Boolean) {
        this.isChecked = isChecked
        postDelayed({ toggle(if (isChecked) STATE_SWITCH_ON else STATE_SWITCH_OFF) }, 300)
    }

    @Synchronized
    private fun toggle(switchState: Int) {
        if (switchState == STATE_SWITCH_ON || switchState == STATE_SWITCH_OFF) {
            if (switchState == STATE_SWITCH_ON && (lastState == STATE_SWITCH_OFF || lastState == STATE_SWITCH_OFF2) || switchState == STATE_SWITCH_OFF && (lastState == STATE_SWITCH_ON || lastState == STATE_SWITCH_ON2)) {
                sAnim = 1f
            }
            bAnim = 1f
            refreshState(switchState)
        }
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(switchView: IosSwitch?, isChecked: Boolean)
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        onCheckedChangeListener = listener
    }

    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.isChecked = isChecked
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        isChecked = ss.isChecked
        this.state = if (isChecked) STATE_SWITCH_ON else STATE_SWITCH_OFF
    }

    internal class SavedState : BaseSavedState {
        var isChecked = false

        constructor(superState: Parcelable?) : super(superState) {}
        private constructor(`in`: Parcel) : super(`in`) {
            isChecked = 1 == `in`.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(if (isChecked) 1 else 0)
        }

        companion object {
            val CREATOR: Parcelable.Creator<SavedState?> =
                object : Parcelable.Creator<SavedState?> {
                    override fun createFromParcel(`in`: Parcel): SavedState? {
                        return SavedState(`in`)
                    }

                    override fun newArray(size: Int): Array<SavedState?> {
                        return arrayOfNulls(size)
                    }
                }
        }

        override fun describeContents(): Int {
            return 0
        }

        object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    companion object {
        /**
         * state switch on
         */
        const val STATE_SWITCH_ON = 4

        /**
         * state prepare to off
         */
        const val STATE_SWITCH_ON2 = 3

        /**
         * state prepare to on
         */
        const val STATE_SWITCH_OFF2 = 2

        /**
         * state switch off
         */
        const val STATE_SWITCH_OFF = 1
    }
}
