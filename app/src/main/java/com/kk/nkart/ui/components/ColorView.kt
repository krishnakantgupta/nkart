package com.kk.nkart.ui.components

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class ColorView {

    private var paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
    }

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        canvas?.drawCircle(viewHalfWidth(), viewHalfWidth(), viewHalfWidth(), paint)
//    }
//
//    fun viewHalfWidth(): Float {
//        return (width / 2).toFloat()
//    }
}