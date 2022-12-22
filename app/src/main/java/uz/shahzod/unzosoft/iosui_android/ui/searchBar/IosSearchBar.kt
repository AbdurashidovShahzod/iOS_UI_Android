package uz.shahzod.unzosoft.iosui_android.ui.searchBar

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * Created by Abdurashidov Shahzod on 22/12/22
 * company QQBank
 * shahzod9933@gmail.com
 */

@SuppressLint("AppCompatCustomView")
class IosSearchBar(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    EditText(context, attrs, defStyleAttr), OnFocusChangeListener, View.OnKeyListener {

    private var isLeft = false
    private var pressSearch = false
    private var listener: OnSearchClickListener? = null
    fun setOnSearchClickListener(listener: OnSearchClickListener?) {
        this.listener = listener
    }

    constructor(context: Context?) : this(context, null) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.editTextStyle
    ) {
        init()
    }

    init {
        init()
    }

    private fun init() {
        onFocusChangeListener = this
        setOnKeyListener(this)
    }

    override fun onDraw(canvas: Canvas) {
        if (isLeft) {
            super.onDraw(canvas)
        } else {
            val drawables = compoundDrawables
            val drawableLeft = drawables[0]
            val drawableRight = drawables[2]
            translate(drawableLeft, canvas)
            translate(drawableRight, canvas)
            super.onDraw(canvas)
        }
    }

    fun translate(drawable: Drawable?, canvas: Canvas) {
        if (drawable != null) {
            val textWidth = paint.measureText(hint.toString())
            val drawablePadding = compoundDrawablePadding
            val drawableWidth = drawable.intrinsicWidth
            val bodyWidth = textWidth + drawableWidth + drawablePadding
            if (drawable === compoundDrawables[0]) {
                canvas.translate((width - bodyWidth - paddingLeft - paddingRight) / 2, 0f)
            } else {
                setPadding(
                    paddingLeft,
                    paddingTop, (width - bodyWidth - paddingLeft).toInt(), paddingBottom
                )
                canvas.translate((width - bodyWidth - paddingLeft) / 2, 0f)
            }
        }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!pressSearch && TextUtils.isEmpty(text.toString())) {
            isLeft = hasFocus
        }
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        pressSearch = keyCode == KeyEvent.KEYCODE_ENTER
        if (pressSearch && listener != null) {
            val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
            }
            if (event.action == KeyEvent.ACTION_UP) {
                listener!!.onSearchClick(v)
            }
        }
        return false
    }

    interface OnSearchClickListener {
        fun onSearchClick(view: View?)
    }

    companion object {
        private val TAG = IosSearchBar::class.java.simpleName
    }
}