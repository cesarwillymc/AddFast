package com.summit.home.home.utils

import android.os.Handler
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.autoScroll(interval: Long) {
    var scrollPosition = 0
    val handler = Handler()
    val runnable = object : Runnable {

        override fun run() {

            try {
                val count = adapter?.itemCount ?: 0

                setCurrentItem(scrollPosition++ % count, true)
                handler.postDelayed(this, interval)
            } catch (e: Exception) {

            }
        }
    }
    val registerCallbak = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            scrollPosition = position + 1
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }
    }
    registerCallbak?.let {
        registerOnPageChangeCallback(it)
    }

    runnable?.let {
        handler.post(it)
    }

}