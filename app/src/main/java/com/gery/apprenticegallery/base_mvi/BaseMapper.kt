package com.gery.apprenticegallery.base_mvi

interface BaseMapper<R, T> {
    fun transform(input: R): T
}
