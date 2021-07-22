package com.kornos.lint.demo;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UExpression;
import org.jetbrains.uast.UField;
import org.jetbrains.uast.ULiteralExpression;
import org.jetbrains.uast.UReferenceExpression;
import org.jetbrains.uast.UastUtils;
import org.jetbrains.uast.util.UastExpressionUtils;

import java.util.ArrayList;
import java.util.List;

public class EventSpaceDetector extends Detector implements Detector.UastScanner {

    static final Issue ISSUE = Issue.create(
            "event_space_issue",    //唯一 ID
            "埋点不允许出现空格",    //简单描述
            "你不知道有时候卵用空格会出问题的吗",  //详细描述
            Category.CORRECTNESS,   //问题种类（正确性、安全性等）
            6,  //权重
            Severity.WARNING,   //问题严重程度（忽略、警告、错误）
            new Implementation(     //实现，包括处理实例和作用域
                    EventSpaceDetector.class,
                    Scope.JAVA_FILE_SCOPE));
    private final String packageName = "com.kronos.sample";


    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        List<Class<? extends UElement>> types = new ArrayList<>();
        types.add(UCallExpression.class);
        return types;
    }

    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler() {

            @Override
            public void visitCallExpression(@NotNull UCallExpression node) {
                checkIsConstructorCall(node);
            }



            private void checkIsConstructorCall(UCallExpression node) {
                if (!UastExpressionUtils.isConstructorCall(node)) {
                    return;
                }
                UReferenceExpression classRef = node.getClassReference();
                try {
                    if (classRef != null) {
                        String className = UastUtils.getQualifiedName(classRef);
                        String value = packageName + ".Event";
                        List<UExpression> args = node.getValueArguments();
                        for (UExpression element : args) {
                            if (element instanceof ULiteralExpression) {
                                Object stringValue = ((ULiteralExpression) element).getValue();
                                if (stringValue instanceof String && stringValue.toString().contains(" ")) {
                                    if (!TextUtils.isEmpty(value) && className.equals(value)) {
                                        context.report(ISSUE, node, context.getLocation(node),
                                                "谁给你的胆子用空格的");
                                    }
                                }
                            }
                            element.getExpressionType();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}

