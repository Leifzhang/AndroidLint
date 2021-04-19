package com.kronos.lint.spi

import com.android.tools.lint.detector.api.Issue

interface LintSpi {
    fun issue(): List<Issue>
}