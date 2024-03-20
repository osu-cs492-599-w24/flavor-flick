import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
    private const val PERMISSION_CODE = 101

    fun generateRecipePdfAndOpen(recipe: RecipeInfo, bitmap: Bitmap?, context: Context) {
        Log.d("PDFGenerator", "Generating PDF")

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Creating Paint objects for drawing text
        val titlePaint = Paint().apply {
            textSize = 15F
            color = ContextCompat.getColor(context, R.color.black)
        }

        try {
            // Drawing text on the canvas
            canvas.drawText(recipe.title, 50F, 100F, titlePaint)
            canvas.drawText(recipe.category, 50F, 120F, titlePaint)
            canvas.drawText(recipe.region, 50F, 140F, titlePaint)
            canvas.drawText(recipe.videoLink, 50F, 160F, titlePaint)
            canvas.drawText(recipe.recipeLink, 50F, 180F, titlePaint)

            // Scale down the bitmap to fit in the PDF
            bitmap?.let {
                val scaledBitmap = Bitmap.createScaledBitmap(it, 120, 120, false)
                canvas.drawBitmap(scaledBitmap, 50F, 200F, null)
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
}
