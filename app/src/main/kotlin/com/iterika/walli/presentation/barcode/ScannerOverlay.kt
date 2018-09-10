package com.iterika.walli.presentation.barcode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.dip

class ScannerOverlay @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : ViewGroup(context, attrs, defStyle) {

    private var left: Float = 0.toFloat()
    private var top: Float = 0.toFloat()
    private var endY: Float = 0.toFloat()
    private val rectWidth: Int by lazy { width - dip(60) }
    private val rectHeight: Int by lazy { dip(190) }
    private val frames: Int = 6
    private var revAnimation: Boolean = false
    private val lineColor: Int = Color.parseColor("#032e52")
    private val lineWidth: Int by lazy { dip(1) }

    private val line2Width: Int by lazy { dip(7) }
    private val line2ColorNormal: Int = Color.parseColor("#032e52")
    private val line2ColorBad: Int = Color.parseColor("#f11b1a")
    private val line2ColorOk: Int = Color.parseColor("#6eb43c")
    private var lines = FloatArray(32)

    var eraser: Paint? = null
    var line: Paint? = null
    var line2: Paint? = null
    var rect: RectF? = null

    init {
        eraser = Paint()
        eraser?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        setWillNotDraw(false)
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        rect = RectF()
        line = Paint()
        line?.setColor(lineColor)
        line?.setStrokeWidth(lineWidth.toFloat())

        line2 = Paint()
        line2?.setColor(line2ColorNormal)
        line2?.setStrokeWidth(line2Width.toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        left = ((w - rectWidth) / 2).toFloat()
        top = ((h - rectHeight) / 2).toFloat()
        endY = top
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun shouldDelayChildPressedState() = false

    public fun setLinesOk() = line2?.setColor(line2ColorOk)
    public fun setLinesNormal() = line2?.setColor(line2ColorNormal)
    public fun setLinesBad() = line2?.setColor(line2ColorBad)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cornerRadius = 0

        rect?.set(left, top, rectWidth + left, rectHeight + top)
        canvas.drawRoundRect(rect, cornerRadius.toFloat(), cornerRadius.toFloat(), eraser)

        if (endY >= top + rectHeight.toFloat() + frames.toFloat()) {
            revAnimation = true
        } else if (endY == top + frames) {
            revAnimation = false
        }

        if (revAnimation) {
            endY -= frames.toFloat()
        } else {
            endY += frames.toFloat()
        }
        canvas.drawLine(left, endY, left + rectWidth, endY, line)

        lines[0]  = left - line2Width / 2
        lines[1]  = top
        lines[2]  = left + dip(20)
        lines[3]  = top

        lines[4]  = left
        lines[5]  = top - line2Width / 2
        lines[6]  = left
        lines[7]  = top + dip(20)

        lines[8]  = rectWidth + left
        lines[9]  = top + dip(20)
        lines[10] = rectWidth + left
        lines[11] = top - line2Width / 2

        lines[12] = rectWidth + left - dip(20)
        lines[13] = top
        lines[14] = rectWidth + left + line2Width / 2
        lines[15] = top

        lines[16] = left - line2Width / 2
        lines[17] = rectHeight + top
        lines[18] = left + dip(20)
        lines[19] = rectHeight + top

        lines[20] = left
        lines[21] = rectHeight + top + line2Width / 2
        lines[22] = left
        lines[23] = rectHeight + top - dip(20)

        lines[24] = rectWidth + left
        lines[25] = rectHeight + top - dip(20)
        lines[26] = rectWidth + left
        lines[27] = rectHeight + top + line2Width / 2

        lines[28] = rectWidth + left - dip(20)
        lines[29] = rectHeight + top
        lines[30] = rectWidth + left + line2Width / 2
        lines[31] = rectHeight + top

        canvas.drawLines(lines, line2)
        invalidate()
    }
}
