package com.gery.apprenticegallery.base_mvi

interface PartialState<VS : ViewState, VE : ViewEffect> {
    fun reduce(previousState: VS): VS = previousState
    fun mapToViewEffect(): VE? = null
}
