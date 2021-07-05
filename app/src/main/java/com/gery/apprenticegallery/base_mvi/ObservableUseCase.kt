package com.gery.apprenticegallery.base_mvi

import io.reactivex.rxjava3.core.Observable

interface ObservableUseCase<in Params, T> {
    fun buildObservable(params: Params): Observable<T>
}
