package com.kornos.lint.demo;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UReferenceExpression;
import org.jetbrains.uast.UastUtils;
import org.jetbrains.uast.util.UastExpressionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Author: Omooo
 * Date: 2019/7/5
 * Desc: Avoid call new Thread() directly
 */
@SuppressWarnings("UnstableApiUsage")
public class ThreadDetector extends Detector implements Detector.UastScanner {

    private final String NEW_THREAD = "java.lang.Thread";
    public static final Issue ISSUE = Issue.create(
            "ThreadUsage",
            "Thread Usage",
            "Please use ThreadPool,such as AsyncTask.SERIAL_EXECUTOR",
            Category.CORRECTNESS,
            6,
            Severity.WARNING,
            new Implementation(ThreadDetector.class, Scope.JAVA_FILE_SCOPE)
    );

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UCallExpression.class);
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler() {
            @Override
            public void visitCallExpression(@NotNull UCallExpression node) {
                if (!UastExpressionUtils.isConstructorCall(node)) {
                    return;
                }
                String className;
                UReferenceExpression classRef = node.getClassReference();
                if (classRef != null) {
                    className = UastUtils.getQualifiedName(classRef);
                    if (NEW_THREAD.equals(className) && context.getProject().isAndroidProject()) {
                        context.report(
                                ISSUE,
                                node,
                                context.getLocation(node),
                                "\u21E2 Avoid call new Thread() directly"
                        );
                    }
                }
            }
        };
    }
}
