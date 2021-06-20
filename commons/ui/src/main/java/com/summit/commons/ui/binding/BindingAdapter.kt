package com.summit.commons.ui.binding

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.summit.commons.ui.R
import com.summit.commons.ui.extension.hide
import com.summit.commons.ui.extension.show
import de.hdodenhof.circleimageview.CircleImageView
import jp.wasabeef.glide.transformations.BlurTransformation
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


@BindingAdapter("app:visibility")
fun setVisibilty(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.show()
    } else {
        view.hide()
    }
}
@BindingAdapter("app:errorText")
fun setError(tInputLayout: EditText, str: String?) {
    if (str.isNullOrEmpty()) {
        tInputLayout.error = null
    } else {
        tInputLayout.error = str

    }
}
@BindingAdapter("app:enabledView")
fun CardView.setEnabledView(enabled: Boolean) {
    this.isEnabled=enabled
    if(enabled){
        setCardBackgroundColor(context.getColor(R.color.enabled))
    }else{
        setCardBackgroundColor(context.getColor(R.color.disable))
    }
}
@set:BindingAdapter("visible")
var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

@set:BindingAdapter("gone")
var View.gone
    get() = visibility == View.GONE
    set(value) {
        visibility = if (value) View.GONE else View.VISIBLE
    }

@set:BindingAdapter("invisible")
var View.invisible
    get() = visibility == View.INVISIBLE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }


/*
@BindingAdapter("app:setWeatherIcon")
fun setWeatherIcon(view: ImageView, iconPath: String?) {
    if (iconPath.isNullOrEmpty())
        return
   // Glide().get().cancelRequest(view)
    val newPath = iconPath.replace(iconPath, "a$iconPath")
    val imageid = view.context.resources.getIdentifier(newPath + "_svg", "drawable", view.context.packageName)
    val imageDrawable = view.context.resources.getDrawable(imageid, null)
    view.setImageDrawable(imageDrawable)
}

@BindingAdapter("app:setErrorView")
fun setErrorView(view: View, viewState: BaseViewState?) {
    if (viewState?.shouldShowErrorMessage() == true)
        view.show()
    else
        view.hide()

    view.setOnClickListener { view.hide() }
}


@BindingAdapter("app:setErrorText")
fun setErrorText(view: TextView, viewState: BaseViewState?,) {
    if (viewState?.shouldShowErrorMessage() == true)
        view.text = viewState.getErrorMessage()!!.message
    else
        view.text = ""
}
 */

@BindingAdapter("imageUrl", requireAll = false)
fun ImageView.imageUrl(url: String?) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("imageUrlCircular", requireAll = false)
fun CircleImageView.imageUrlCircular(url: String?) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("imageUrlBlur", requireAll = false)
fun ImageView.imageUrlBlur(url: String?) {
    Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(BlurTransformation(5, 2))).into(this)
}
@BindingAdapter("timeAgo", requireAll = false)
fun TextView.timeAgo(time: Long) {
    val prettyTime = PrettyTime(Locale.getDefault())
    val ago: String = prettyTime.format(Date(time))
    text=ago
}


