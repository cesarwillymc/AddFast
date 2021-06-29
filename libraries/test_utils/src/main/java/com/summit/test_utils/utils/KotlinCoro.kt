package com.summit.test_utils.utils

import com.google.android.gms.tasks.Task
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

@MockK
fun <T> Task<T>.await(): T {
    return result as T
}