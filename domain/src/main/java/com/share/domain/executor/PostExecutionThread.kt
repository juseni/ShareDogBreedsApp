package com.share.domain.executor

import io.reactivex.rxjava3.core.Scheduler

/**
 * @author Juan Sebastian Niño
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}