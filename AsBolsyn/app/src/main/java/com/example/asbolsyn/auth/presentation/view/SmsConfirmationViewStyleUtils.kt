package com.example.asbolsyn.auth.presentation.view

import android.content.Context
import android.util.AttributeSet
import com.example.asbolsyn.R
import com.example.asbolsyn.utils.extensions.getCompatColor

internal object SmsConfirmationViewStyleUtils {

    private var defaultStyle: SmsConfirmationView.Style? = null

    fun getDefault(context: Context): SmsConfirmationView.Style {
        if (defaultStyle == null) {
            val resources = context.resources
            val symbolViewStyle = SymbolView.Style(
                showCursor = true,
                width = resources.getDimensionPixelSize(R.dimen.symbol_view_width),
                height = resources.getDimensionPixelSize(R.dimen.symbol_view_height),
                backgroundColor = context.getCompatColor(R.color.color_white),
                borderColor = context.getCompatColor(R.color.color_primary),
                borderColorActive = context.getCompatColor(R.color.color_primary),
                borderWidth = resources.getDimensionPixelSize(R.dimen.symbol_view_stroke_width),
                borderCornerRadius = resources.getDimension(R.dimen.symbol_view_corner_radius),
                textColor = context.getCompatColor(R.color.color_text_main),
                textSize = resources.getDimensionPixelSize(R.dimen.symbol_view_text_size)
            )
            defaultStyle = SmsConfirmationView.Style(
                codeLength = SmsConfirmationView.DEFAULT_CODE_LENGTH,
                symbolsSpacing = resources.getDimensionPixelSize(R.dimen.symbols_spacing),
                symbolViewStyle = symbolViewStyle,
            )
        }
        return defaultStyle!!
    }

    fun getFromAttributes(
        attrs: AttributeSet,
        context: Context
    ): SmsConfirmationView.Style {

        val defaultStyle: SmsConfirmationView.Style = getDefault(context)
        val defaultSymbolStyle: SymbolView.Style = defaultStyle.symbolViewStyle
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SmsConfirmationView, 0, 0)

        return with(typedArray) {
            val showCursor: Boolean = getBoolean(
                R.styleable.SmsConfirmationView_scv_showCursor,
                defaultSymbolStyle.showCursor
            )
            val symbolWidth: Int = getDimensionPixelSize(
                R.styleable.SmsConfirmationView_scv_symbolWidth,
                defaultSymbolStyle.width
            )
            val symbolHeight: Int = getDimensionPixelSize(
                R.styleable.SmsConfirmationView_scv_symbolHeight,
                defaultSymbolStyle.height
            )
            val symbolBackgroundColor: Int = getColor(
                R.styleable.SmsConfirmationView_scv_symbolBackgroundColor,
                defaultSymbolStyle.backgroundColor
            )
            val symbolBorderColor: Int = getColor(
                R.styleable.SmsConfirmationView_scv_symbolBorderColor,
                defaultSymbolStyle.borderColor
            )
            val symbolBorderActiveColor: Int = getColor(
                R.styleable.SmsConfirmationView_scv_symbolBorderActiveColor,
                symbolBorderColor
            )
            val symbolBorderWidth: Int = getDimensionPixelSize(
                R.styleable.SmsConfirmationView_scv_symbolBorderWidth,
                defaultSymbolStyle.borderWidth
            )
            val symbolTextColor: Int = getColor(
                R.styleable.SmsConfirmationView_scv_symbolTextColor,
                defaultSymbolStyle.textColor
            )
            val symbolTextSize: Int = getDimensionPixelSize(
                R.styleable.SmsConfirmationView_scv_symbolTextSize,
                defaultSymbolStyle.textSize
            )
            val cornerRadius: Float = getDimension(
                R.styleable.SmsConfirmationView_scv_symbolBorderCornerRadius,
                defaultSymbolStyle.borderCornerRadius
            )
            val codeLength: Int = getInt(
                R.styleable.SmsConfirmationView_scv_codeLength,
                defaultStyle.codeLength
            )
            val symbolsSpacingPx: Int = getDimensionPixelSize(
                R.styleable.SmsConfirmationView_scv_symbolsSpacing,
                defaultStyle.symbolsSpacing
            )
            val smsDetectionMode: SmsConfirmationView.SmsDetectionMode = getInt(
                R.styleable.SmsConfirmationView_scv_smsDetectionMode,
                SmsConfirmationView.SmsDetectionMode.AUTO.ordinal
            ).let { SmsConfirmationView.SmsDetectionMode.values()[it] }

            recycle()

            SmsConfirmationView.Style(
                codeLength = codeLength,
                symbolsSpacing = symbolsSpacingPx,
                symbolViewStyle = SymbolView.Style(
                    showCursor = showCursor,
                    width = symbolWidth,
                    height = symbolHeight,
                    backgroundColor = symbolBackgroundColor,
                    borderColor = symbolBorderColor,
                    borderColorActive = symbolBorderActiveColor,
                    borderWidth = symbolBorderWidth,
                    borderCornerRadius = cornerRadius,
                    textColor = symbolTextColor,
                    textSize = symbolTextSize
                ),
                smsDetectionMode = smsDetectionMode
            )
        }
    }
}