package com.iterika.walli.presentation.document

import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.iterika.walli.GlideApp
import com.iterika.walli.R
import org.jetbrains.anko.dip

class ImageFragment : DialogFragment() {

    private var path: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frameLayout = FrameLayout(activity!!)

        val imageView = ImageView(activity)
        val w = activity!!.windowManager
        val size = Point()
        w.defaultDisplay.getSize(size)
        imageView.minimumWidth = size.x
        imageView.minimumHeight = size.y
        imageView.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        path = arguments?.getSerializable("image_path") as String

        GlideApp.with(this).load(path).dontAnimate().into(imageView)

        imageView.setOnClickListener { dismiss() }

        val closeImageView = ImageView(activity)
        val width = context?.dip(36) ?: 0
        closeImageView.layoutParams = FrameLayout.LayoutParams(width, width, Gravity.END)
        closeImageView.setImageResource(R.drawable.close)
        frameLayout.setPaddingRelative(0, width, 0,0)

        closeImageView.setOnClickListener { dismiss() }

        frameLayout.addView(imageView)
        frameLayout.addView(closeImageView)

        return frameLayout
    }

    companion object {

        fun newInstance(imagePath: String): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putSerializable("image_path", imagePath)
            fragment.arguments = args
            fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TransparentDialog)
            return fragment
        }
    }
}
