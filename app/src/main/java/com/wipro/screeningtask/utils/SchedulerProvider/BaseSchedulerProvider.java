package com.wipro.screeningtask.utils.SchedulerProvider;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    Scheduler io();

    Scheduler mainThread();
}
