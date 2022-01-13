package com.kornos.lint.demo;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.*;
import com.intellij.psi.PsiMethod;

import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.*;
import org.jetbrains.uast.util.UastExpressionUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteDetector extends Detector implements Detector.UastScanner {
    private final String WM_ROUTER_PACKAGE = "com.sankuai.waimai.router";
    private final String WM_ROUTER_ANNOTATION = WM_ROUTER_PACKAGE + ".annotation.RouterPage";
    private final String WM_ROUTER_CALL = WM_ROUTER_PACKAGE + ".Router";

    static final Issue ISSUE = Issue.create(
            "router_annotation_issue",    //唯一 ID
            "不允许使用该注解",    //简单描述
            "全局项目不允许使用该注解 请更换RouterUri",  //详细描述
            Category.CORRECTNESS,   //问题种类（正确性、安全性等）
            6,  //权重
            Severity.WARNING,   //问题严重程度（忽略、警告、错误）
            new Implementation(     //实现，包括处理实例和作用域
                    RouteDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    static final Issue CALL_ISSUE = Issue.create("router_call_issue",    //唯一 ID
            "不要直接引用WM router",    //简单描述
            "使用项目封装的路由中间件完成跳转",  //详细描述
            Category.CORRECTNESS,   //问题种类（正确性、安全性等）
            6,  //权重
            Severity.WARNING,   //问题严重程度（忽略、警告、错误）
            new Implementation(     //实现，包括处理实例和作用域
                    RouteDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        List<Class<? extends UElement>> types = new ArrayList<>();
        types.add(UAnnotation.class);
        types.add(UCallExpression.class);
        return types;
    }

    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler() {

            @Override
            public void visitAnnotation(@NotNull UAnnotation node) {
                isAnnotation(node);
            }

            private void isAnnotation(UAnnotation node) {
                String type = node.getQualifiedName();
                if (WM_ROUTER_ANNOTATION.equals(type)) {
                    context.report(ISSUE, node, context.getLocation(node),
                            "该注解不允许使用");
                }
            }

            @Override
            public void visitClass(@NotNull UClass node) {
                super.visitClass(node);
            }

            @Override
            public void visitCallExpression(@NotNull UCallExpression node) {
                checkIsMethod(node);
                checkIsConstructorCall(node);
            }

            private void checkIsConstructorCall(UCallExpression node) {
                if (!UastExpressionUtils.isConstructorCall(node)) {
                    return;
                }
                try {
                    UReferenceExpression classRef = node.getClassReference();
                    if (classRef != null) {
                        String className = UastUtils.getQualifiedName(classRef);
                        String uriValue = WM_ROUTER_PACKAGE + ".common.DefaultUriRequest";
                        String pageValue = WM_ROUTER_PACKAGE + ".common.DefaultPageUriRequest";

                        if (className.equals(uriValue) || className.equals(pageValue)) {
                            context.report(CALL_ISSUE, node, context.getLocation(node),
                                    "请使用项目提供的路由中间件 ");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void checkIsMethod(UCallExpression node) {
                try {
                    if (UastExpressionUtils.isMethodCall(node)) {
                        if (node.getReceiver() != null && node.getMethodName() != null) {
                            PsiMethod method = node.resolve();
                            if (context.getEvaluator().isMemberInClass(method, WM_ROUTER_CALL)) {
                                context.report(CALL_ISSUE, node, context.getLocation(node),
                                        "请使用项目提供的路由中间件");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
    }

}
