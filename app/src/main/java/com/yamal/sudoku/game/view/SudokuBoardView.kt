package com.yamal.sudoku.game.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.VisibleForTesting

import com.yamal.sudoku.model.SudokuCellValue
import com.yamal.sudoku.R
import com.yamal.sudoku.commons.utils.getColorFromAttr
import com.yamal.sudoku.model.ReadOnlyBoard

class SudokuBoardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    var listener: OnCellSelectedListener? = null

    private var isHighlighted = false
    private var readOnlyBoard: ReadOnlyBoard? = null
    private var cellWidth: Float = 0F
    private var boardWidth: Float = 0F
    private var textHeight: Float = 0F
    private var textX: Float = 0F
    private var softPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColorFromAttr(R.attr.colorBoardSoftLine)
        style = Paint.Style.STROKE
        strokeWidth = context.resources.getDimension(R.dimen.sudoku_soft_width)
    }
    private val prominentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColorFromAttr(R.attr.colorBoardProminentLine)
        style = Paint.Style.STROKE
        strokeWidth = context.resources.getDimension(R.dimen.sudoku_prominent_line_width)
    }
    private val textPaint = TextPaint().apply {
        color = context.getColorFromAttr(R.attr.colorBoardText)
        setPadding(0, 0, 0, 0)
    }
    private val selectedCellPaint = Paint().apply {
        color = context.getColorFromAttr(R.attr.colorBoardSelectedCell)
        style = Paint.Style.FILL
    }
    private val fixedCellPaint = Paint().apply {
        color = context.getColorFromAttr(R.attr.colorBoardFixedCell)
        style = Paint.Style.FILL
    }
    private val fixedAndSelectedPaint = Paint().apply {
        color = context.getColorFromAttr(R.attr.colorBoardFixedAndSelectedCell)
        style = Paint.Style.FILL
    }
    private val highlightedPaint = Paint().apply {
        color = context.getColorFromAttr(R.attr.colorBoardHighlightedCell)
        style = Paint.Style.FILL
    }

    @VisibleForTesting
    val board: ReadOnlyBoard?
        get() = readOnlyBoard

    fun highlightBackground() {
        isHighlighted = true
        invalidate()
    }

    fun unHighlightBackground() {
        isHighlighted = false
        invalidate()
    }

    fun setBoard(onlyBoard: ReadOnlyBoard?) {
        onlyBoard?.let {
            this.readOnlyBoard = onlyBoard
            invalidate()
        }
    }

    @Suppress("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val intWidth = MeasureSpec.getSize(widthMeasureSpec)
        boardWidth = intWidth.toFloat()
        cellWidth = boardWidth / 9F
        textHeight = cellWidth * 0.8F
        textPaint.textSize = textHeight
        textX = (cellWidth / 2) - (textPaint.measureText("0") / 2)

        setMeasuredDimension(intWidth, intWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawNumbers()
        canvas.drawBoard()
    }

    private fun Canvas.drawNumbers() {
        readOnlyBoard?.let { board ->
            if (isHighlighted) {
                drawRect(0F, 0F, boardWidth, boardWidth, highlightedPaint)
            }

            for (i in 0..8) {
                for (j in 0..8) {
                    val cell = board[i, j]
                    if (!isHighlighted) {
                        var paint: Paint? = null
                        val cellIsSelected = i == board.getSelectedX() && j == board.getSelectedY()

                        when {
                            cellIsSelected && cell.isFixed && cell.value != SudokuCellValue.EMPTY -> {
                                paint = fixedAndSelectedPaint
                            }
                            cellIsSelected ->
                                paint = selectedCellPaint
                            cell.isFixed && cell.value != SudokuCellValue.EMPTY ->
                                paint = fixedCellPaint
                        }

                        paint?.let { drawColouredCell(i, j, it) }
                    }

                    drawText(
                        cell.value.toString(),
                        j * cellWidth + textX,
                        i * cellWidth + textHeight,
                        textPaint
                    )
                }
            }
        }
    }

    private fun Canvas.drawColouredCell(x: Int, y: Int, paint: Paint) {
        drawRect(
            y * cellWidth,
            x * cellWidth,
            y * cellWidth + cellWidth,
            x * cellWidth + cellWidth,
            paint
        )
    }

    private fun Canvas.drawBoard() {
        // Outline rectangle
        drawRect(0F, 0F, boardWidth, boardWidth, prominentPaint)

        // Vertical lines
        drawLine(1 * cellWidth, 0F, 1 * cellWidth, boardWidth, softPaint)
        drawLine(2 * cellWidth, 0F, 2 * cellWidth, boardWidth, softPaint)
        drawLine(3 * cellWidth, 0F, 3 * cellWidth, boardWidth, prominentPaint)
        drawLine(4 * cellWidth, 0F, 4 * cellWidth, boardWidth, softPaint)
        drawLine(5 * cellWidth, 0F, 5 * cellWidth, boardWidth, softPaint)
        drawLine(6 * cellWidth, 0F, 6 * cellWidth, boardWidth, prominentPaint)
        drawLine(7 * cellWidth, 0F, 7 * cellWidth, boardWidth, softPaint)
        drawLine(8 * cellWidth, 0F, 8 * cellWidth, boardWidth, softPaint)

        // Horizontal lines
        drawLine(0F, 1 * cellWidth, boardWidth, 1 * cellWidth, softPaint)
        drawLine(0F, 2 * cellWidth, boardWidth, 2 * cellWidth, softPaint)
        drawLine(0F, 3 * cellWidth, boardWidth, 3 * cellWidth, prominentPaint)
        drawLine(0F, 4 * cellWidth, boardWidth, 4 * cellWidth, softPaint)
        drawLine(0F, 5 * cellWidth, boardWidth, 5 * cellWidth, softPaint)
        drawLine(0F, 6 * cellWidth, boardWidth, 6 * cellWidth, prominentPaint)
        drawLine(0F, 7 * cellWidth, boardWidth, 7 * cellWidth, softPaint)
        drawLine(0F, 8 * cellWidth, boardWidth, 8 * cellWidth, softPaint)
    }

    @Suppress("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                listener?.onCellSelected(
                    x = (event.y / cellWidth).toInt(),
                    y = (event.x / cellWidth).toInt()
                )
            }
        }
        return super.onTouchEvent(event)
    }

    interface OnCellSelectedListener {
        fun onCellSelected(x: Int, y: Int)
    }
}