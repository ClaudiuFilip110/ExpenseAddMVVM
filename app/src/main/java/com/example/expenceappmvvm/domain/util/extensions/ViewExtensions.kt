package com.example.expenceappmvvm.domain.util.extensions

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun Fragment.toast(message: String) = Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.toast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Fragment.longToast(message: String) = Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
fun Activity.longToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
fun Context.longToast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachedToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachedToRoot)

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun readAssetFile(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
}

fun ImageView.loadUrl(url: String?, placeholderRes: Int = 0, listener: RequestListener<Drawable>? = null) {
    Glide.with(context).load(url)
        .placeholder(placeholderRes)
        .error(placeholderRes)
        .fallback(placeholderRes)
        .addListener(listener)
        .into(this)
}

fun ImageView.loadUrl(url: String?, requestOptions: RequestOptions) {
    Glide.with(context).load(url).apply(requestOptions).into(this)
}