import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import edu.oregonstate.cs492.finalProject.R
import edu.oregonstate.cs492.finalProject.data.RecipeInfo
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PDFGenerator {

    private const val PAGE_HEIGHT = 1120
    private const val PAGE_WIDTH = 792
    private const val MARGIN = 50F

    fun generateRecipePdfAndOpen(recipe: RecipeInfo, bitmap: Bitmap?, context: Context) {
        Log.d("PDFGenerator", "Generating PDF")

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Text sizes/styles
        val titlePaint = Paint().apply {
            textSize = 40F // Larger font size for titles
            isFakeBoldText = true
            color = ContextCompat.getColor(context, R.color.black)
            textAlign = Paint.Align.LEFT
        }

        val textPaint = Paint().apply {
            textSize = 30F // Medium font size for content
            color = ContextCompat.getColor(context, R.color.black)
            textAlign = Paint.Align.LEFT
        }

        val smallTextPaint = Paint().apply {
            textSize = 20F // Smaller font size for links
            color = ContextCompat.getColor(context, R.color.black)
            textAlign = Paint.Align.LEFT
        }

        try {
            // Text
            drawTextWithWrapping(canvas, "Recipe: ${recipe.title}", titlePaint, 150F, MARGIN)
            drawTextWithWrapping(canvas, "Category: ${recipe.category}", textPaint, 210F, MARGIN)
            drawTextWithWrapping(canvas, "Region: ${recipe.region}", textPaint, 250F, MARGIN)
            drawTextWithWrapping(canvas, "Video Link: ${recipe.videoLink}", smallTextPaint, 310F, MARGIN)
            drawTextWithWrapping(canvas, "Recipe Link: ${recipe.recipeLink}", smallTextPaint, 340F, MARGIN)

            // Image
            bitmap?.let {
                val scaledBitmap = Bitmap.createScaledBitmap(it, 300, 300, false) // Larger image size
                canvas.drawBitmap(scaledBitmap, MARGIN, 450F, null)
            }

            pdfDocument.finishPage(page)

            val fileDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            } else {
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            }

            val fileName = "${recipe.title}.pdf"
            val file = File(fileDir, fileName)
            pdfDocument.writeTo(FileOutputStream(file))

            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(contentUri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)

        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("PDFGenerator", "Failed to generate PDF: ${e.message}")
            Toast.makeText(context, "Failed to generate PDF file", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
            Log.d("PDFGenerator", "PDF document closed")
        }
    }

    private fun drawTextWithWrapping(canvas: Canvas, text: String, paint: Paint, startY: Float, margin: Float) {
        val maxWidth = PAGE_WIDTH - 2 * margin
        val lineHeight = -paint.ascent() + paint.descent() // Height of a single line of text
        var remainingText = text

        var y = startY
        while (remainingText.isNotBlank()) {
            val measuredWidth = paint.measureText(remainingText)
            if (measuredWidth <= maxWidth) {
                canvas.drawText(remainingText, margin, y, paint)
                break
            }

            var lineBreakIndex = paint.breakText(remainingText, true, maxWidth, null)
            if (lineBreakIndex <= 0) {
                lineBreakIndex = 1
            }

            val line = remainingText.substring(0, lineBreakIndex)
            canvas.drawText(line, margin, y, paint)
            y += lineHeight
            remainingText = remainingText.substring(lineBreakIndex).trim()
        }
    }
}
