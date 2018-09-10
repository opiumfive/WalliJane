package com.iterika.walli

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule class OkHttpGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, IMAGE_CACHE_SIZE.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    }

    companion object {
        private val IMAGE_CACHE_SIZE = 30 * 1024 * 1024 // 30 MiB
    }
}
