package com.kk.nkart.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.kk.nkart.R

class CustomNavigationItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyle) {
    private lateinit var tvTitle: TextView
    private lateinit var viewSelection: View
    private lateinit var icon: ImageView

    private var title: String? = null
    private var iconResource: Int = 0

    @ColorInt
    private var normalColor: Int = context.resources.getColor(R.color.black_7)

    @ColorInt
    private var selectedColor: Int = context.resources.getColor(R.color.colorPrimary)
    private var isItemSelected: Boolean = false;

    init {
        LayoutInflater.from(context).inflate(R.layout.item_navigation_drawer, this, true)
        parseAttributes(context, attrs, defStyle)
        setupView()
    }

    private fun parseAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CustomNavigationItem,
                defStyleAttr,
                0
            )
            try {
                title = typedArray.getString(R.styleable.CustomNavigationItem_title)
                iconResource = typedArray.getResourceId(
                    R.styleable.CustomNavigationItem_icon,
                    0
                )
                selectedColor = typedArray.getColor(
                    R.styleable.CustomNavigationItem_selectedColor,
                    ContextCompat.getColor(context, R.color.black_7)
                )
                normalColor = typedArray.getColor(
                    R.styleable.CustomNavigationItem_normalColor,
                    ContextCompat.getColor(context, R.color.black_7)
                )
                isItemSelected =
                    typedArray.getBoolean(R.styleable.CustomNavigationItem_isSelected, false)
            } finally {
                typedArray.recycle()
            }
        }
    }


    private fun setupView() {
        tvTitle = findViewById<View>(R.id.title) as TextView
        icon = findViewById<View>(R.id.icon) as ImageView
        viewSelection = findViewById<View>(R.id.checkView)
        if (title != null) {
            tvTitle.text = title
        }
        if (iconResource != 0) {
            icon.setImageResource(iconResource!!)
        }
        setItemSelected(isItemSelected)
    }

    fun setItemSelected(selected: Boolean) {
        isItemSelected = selected
        if (isItemSelected) {
//            viewSelection.visibility = View.VISIBLE
            tvTitle.setTextColor(selectedColor)
            icon.setColorFilter(selectedColor)
        } else {
//            viewSelection.visibility = View.INVISIBLE
            tvTitle.setTextColor(normalColor)
            icon.setColorFilter(normalColor)
        }
    }


}