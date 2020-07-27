package com.kornos.lint.demo;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.util.UastExpressionUtils;

import java.util.ArrayList;
import java.util.List;

public class GlideUnusedDetector extends Detector implements Detector.UastScanner {

    static final Issue ISSUE = Issue.create(
            "glide_code_issue",    //唯一 ID
            "不允许直接使用glide",    //简单描述
            "当前项目已经集成了图片中间件，所有图片操作全部移动到中间件操作",  //详细描述
            Category.CORRECTNESS,   //问题种类（正确性、安全性等）
            6,  //权重
            Severity.ERROR,   //问题严重程度（忽略、警告、错误）
            new Implementation(     //实现，包括处理实例和作用域
                    GlideUnusedDetector.class,
                    Scope.JAVA_FILE_SCOPE));

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
                if (!UastExpressionUtils.isMethodCall(node)) {
                    return;
                }
                if (node.getReceiver() != null
                        && node.getMethodName() != null) {
                    String methodName = node.getMethodName();
                    if (methodName.equals("with")) {
                        PsiMethod method = node.resolve();
                        String value = "com.bumptech.glide.Glide";
                        if (context.getEvaluator().isMemberInClass(method, value)) {
                            context.report(ISSUE, node, context.getLocation(node),
                                    "请使用项目提供的路由中间件");
                        }
                    }
                    if (methodName.equals("decodeFile") || methodName.equals("decodeResourceStream")
                            || methodName.equals("decodeResource")) {
                        PsiMethod method = node.resolve();
                        String value = "android.graphics.BitmapFactory";
                        if (context.getEvaluator().isMemberInClass(method, value)) {
                            context.report(ISSUE, node, context.getLocation(node),
                                    "请使用项目提供的路由中间件");
                        }
                    }
                }
            }

        };
    }

}
