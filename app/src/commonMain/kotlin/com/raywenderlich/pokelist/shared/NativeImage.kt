package com.raywenderlich.pokelist.shared

expect class Image

expect fun ByteArray.toNativeImage(): Image?
