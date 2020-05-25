package studio.bz_soft.companyinfo.root

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter.ISO_ZONED_DATE_TIME
import org.threeten.bp.format.DateTimeFormatter.ofPattern
import retrofit2.HttpException
import studio.bz_soft.companyinfo.BuildConfig
import studio.bz_soft.companyinfo.data.http.parseError
import studio.bz_soft.companyinfo.data.http.parseHttpError
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.*

fun textWatcher(afterTextChanged: (String) -> Unit): TextWatcher =
    object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            afterTextChanged(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

var EditText.value: String
    get() = this.text.toString()
    set(value) {
        this.setText(value)
    }

fun showToast(v: View, text: String) {
    Toast.makeText(v.context, biggerText(text), Toast.LENGTH_LONG).show()
}

fun showToast(c: Context, text: String) {
    Toast.makeText(c, biggerText(text), Toast.LENGTH_LONG).show()
}

private fun biggerText(text: String): String {
    val biggerText = SpannableStringBuilder(text)
    biggerText.setSpan(RelativeSizeSpan(1.35f), 0, text.length, 0)
    return biggerText.toString()
}

fun <V> ifIsNull(v: V): Boolean = v?.let { false } ?: true

fun showError(c: Context, ex: HttpException, @StringRes messageId: Int, logTag: String) {
    val error = parseHttpError(ex)
    showToast(c, "${c.getString(messageId)}. $error")
    if (BuildConfig.DEBUG) Log.d(logTag, "Error is => $error")
}

fun showError(c: Context, ex: Exception, @StringRes messageId: Int, logTag: String) {
    val error = parseError(ex)
    showToast(c, "${c.getString(messageId)}. $error")
    if (BuildConfig.DEBUG) Log.d(logTag, "Error is => $error")
}

fun drawable(v: View, @DrawableRes res: Int): Drawable = v.resources.getDrawable(res, null)

fun scrollToPosition(recyclerView: RecyclerView, position: Int) =
    Handler().postDelayed({ recyclerView.scrollToPosition(position) }, 200)

fun toUri(url: URL): Uri = Uri.parse(url.toURI().toString())

fun readJsonFromAsset(c: Context?, file: String): String? =
    try {
        c?.assets?.open(file)?.bufferedReader().use { it?.readText() }
    } catch (ex: IOException) {
        null
    }

fun Double.round(d: Int = 3): Double = "%.${d}f".format(this).toDouble()