package com.share.domain.executor

/**
 * @author Juan Sebastian Niño
 */
class InmediateThreadExecutor : ThreadExecutor {
    override fun execute(runnable: Runnable?) {
        runnable?.run()
    }
}