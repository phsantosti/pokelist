package com.raywenderlich.pokelist.shared

import kotlinx.cinterop.*
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIImage

actual typealias Image = UIImage

@ExperimentalUnsignedTypes
actual fun ByteArray.toNativeImage(): Image? =
    memScoped {
        toCValues()
            .ptr
            .let { NSData.dataWithBytes(it, size.toULong()) }
            .let { UIImage.imageWithData(it) }
    }