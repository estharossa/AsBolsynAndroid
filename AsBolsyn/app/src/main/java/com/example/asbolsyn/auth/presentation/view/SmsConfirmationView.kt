package com.example.asbolsyn.auth.presentation.view

import android.annotation.SuppressLint
import android.content.*
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.Space
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import com.example.asbolsyn.utils.CommonConstants
import com.example.asbolsyn.utils.hideSoftKeyboard
import com.example.asbolsyn.utils.showSoftKeyboard

class SmsConfirmationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        internal const val DEFAULT_CODE_LENGTH = 4
        private const val KEYBOARD_AUTO_SHOW_DELAY = 500L
    }

    var enteredCode: String = CommonConstants.DEFAULT_STRING
        set(value) {
            require(value.length <= codeLength) { "enteredCode=$value is longer than $codeLength" }
            field = value
            onChangeListener?.onCodeChange(
                code = value,
                isComplete = value.length == codeLength
            )
            updateState()
        }

    var onChangeListener: OnChangeListener? = null

    internal var style: Style = SmsConfirmationViewStyleUtils.getDefault(context)
        set(value) {
            if (field == value) return
            field = value
            removeAllViews()
            updateState()
        }

    private val smsDetectionMode: SmsDetectionMode get() = style.smsDetectionMode

    private val symbolSubviews: Sequence<SymbolView>
        get() = children.filterIsInstance<SymbolView>()

    init {
        orientation = HORIZONTAL
        isFocusable = true
        isFocusableInTouchMode = true

        style = if (attrs == null) {
            SmsConfirmationViewStyleUtils.getDefault(context)
        } else {
            SmsConfirmationViewStyleUtils.getFromAttributes(attrs, context)
        }
        updateState()
    }

    private fun updateState() {
        val codeLengthChanged = codeLength != symbolSubviews.count()
        if (codeLengthChanged) {
            setupSymbolSubviews()
        }

        val viewCode = symbolSubviews
            .map { it.state.symbol }
            .filterNotNull()
            .joinToString(separator = CommonConstants.DEFAULT_STRING)
        val isViewCodeOutdated = enteredCode != viewCode
        if (isViewCodeOutdated) {
            symbolSubviews.forEachIndexed { index, view ->
                view.state = SymbolView.State(
                    symbol = enteredCode.getOrNull(index),
                    isActive = (enteredCode.length == index)
                )
            }
        }
    }

    private fun setupSymbolSubviews() {
        removeAllViews()

        for (i in 0 until codeLength) {
            val symbolView = SymbolView(context, style.symbolViewStyle)
            symbolView.state = SymbolView.State(isActive = (i == enteredCode.length))
            addView(symbolView)

            if (i < codeLength.dec()) {
                val space = Space(context).apply {
                    layoutParams = ViewGroup.LayoutParams(style.symbolsSpacing, 0)
                }
                addView(space)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        setOnKeyListener { _, keyCode, event -> handleKeyEvent(keyCode, event) }
//        postDelayed(KEYBOARD_AUTO_SHOW_DELAY) {
//            if (!isGone) {
//                requestFocus()
//                showSoftKeyboard(context)
//            }
//        }

        if (smsDetectionMode == SmsDetectionMode.DISABLED) return
    }

    private fun handleKeyEvent(keyCode: Int, event: KeyEvent): Boolean = when {
        event.action != KeyEvent.ACTION_DOWN -> false
        event.isDigitKey() -> {
            val enteredSymbol = event.keyCharacterMap.getNumber(keyCode)
            appendSymbol(enteredSymbol)
            true
        }
        event.keyCode == KeyEvent.KEYCODE_DEL -> {
            removeLastSymbol()
            true
        }
        event.keyCode == KeyEvent.KEYCODE_ENTER -> {
            hideSoftKeyboard(context)
            true
        }
        else -> false
    }

    private fun KeyEvent.isDigitKey() = keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9

    private fun appendSymbol(symbol: Char) {
        if (enteredCode.length == codeLength) {
            return
        }

        this.enteredCode = enteredCode + symbol
    }

    private fun removeLastSymbol() {
        if (enteredCode.isEmpty()) {
            return
        }

        this.enteredCode = enteredCode.substring(0, enteredCode.length - 1)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && requestFocus()) {
            showSoftKeyboard(context)
            return true
        }

        return super.onTouchEvent(event)
    }

    override fun onCheckIsTextEditor(): Boolean = true

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        with(outAttrs) {
            inputType = InputType.TYPE_CLASS_NUMBER
            imeOptions = EditorInfo.IME_ACTION_DONE
        }

        return BaseInputConnection(this, false)
    }

    fun interface OnChangeListener {
        fun onCodeChange(code: String, isComplete: Boolean)
    }

    internal data class Style(
        val codeLength: Int,
        val symbolsSpacing: Int,
        val symbolViewStyle: SymbolView.Style,
        val smsDetectionMode: SmsDetectionMode = SmsDetectionMode.AUTO
    )

    internal enum class SmsDetectionMode {
        DISABLED,
        AUTO,
        MANUAL
    }
}