package com.kronos.lint.sample.spi

import com.android.tools.lint.detector.api.Issue
import com.google.auto.service.AutoService
import com.kronos.lint.spi.LintSpi


@AutoService(LintSpi::class)
class SampleLintSpi : LintSpi {

    override fun issue(): List<Issue> {
        return mutableListOf<Issue>().apply {
            add(AlertDialogDetector.ISSUE)
        }
    }
}