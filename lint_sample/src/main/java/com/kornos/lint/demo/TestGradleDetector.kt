package com.kornos.lint.demo

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.GradleContext

/**
 *
 *  @Author LiABao
 *  @Since 2022/2/15
 *
 */
class TestGradleDetector : Detector(), Detector.GradleScanner {

    override fun checkDslPropertyAssignment(
        context: GradleContext,
        property: String,
        value: String,
        parent: String,
        parentParent: String?,
        valueCookie: Any,
        statementCookie: Any
    ) {

    }


}