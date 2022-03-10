package com.share.dogbreeds.di

import com.share.dogbreeds.executors.JobExecutor
import com.share.dogbreeds.executors.UIThread
import com.share.domain.executor.PostExecutionThread
import com.share.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module

/**
 * @author Juan Sebastian Niño
 */
@Module
abstract class ExecutorModule {
    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread
}