package com.briak.newsclient.ui.base

import kotlinx.coroutines.experimental.Job

interface JobHolder {
    val job: Job
}