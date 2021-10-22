package com.nads.dindinnapp.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

class EffectsObserver<E>(
    private val effects: Observable<E>,
    private val executeEffect: (E) -> Unit
) : DefaultLifecycleObserver {

    private var disposable: Disposable? = null

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        disposable = effects.flatMap {
            Observable.fromCallable { executeEffect(it) }
        }.subscribe()
    }

    override fun onStop(owner: LifecycleOwner) {
        disposable!!.dispose()
        super.onStop(owner)
    }
}