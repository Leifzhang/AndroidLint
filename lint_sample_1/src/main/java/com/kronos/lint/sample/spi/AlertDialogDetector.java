package com.kronos.lint.sample.spi;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UReferenceExpression;
import org.jetbrains.uast.UastUtils;
import org.jetbrains.uast.util.UastExpressionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LiABao
 * @Since 2020/10/12
 */
public class AlertDialogDetector extends Detector implements Detector.UastScanner {

    private final String ANDROIDX_PACKAGE = "androidx.appcompat.app";
    private final String ALERT_DIALOG_BUILDER = ANDROIDX_PACKAGE + ".AlertDialog.Builder";
    private final String ALERT_DIALOG = ANDROIDX_PACKAGE + ".AlertDialog";

    public static final Issue ISSUE = Issue.create(
            "alert_dialog_issue",    //唯一 ID
            "更换AlertDialog到BiliCommonDialog",    //简单描述
            "全局项目不允许使用该类 请更换BiliCommonDialog",  //详细描述
            Category.CORRECTNESS,   //问题种类（正确性、安全性等）
            6,  //权重
            Severity.WARNING,   //问题严重程度（忽略、警告、错误）
            new Implementation(     //实现，包括处理实例和作用域
                    AlertDialogDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        List<Class<? extends UElement>> types = new ArrayList<>();
        types.add(UCallExpression.class);
        return types;
    }


    @Override
    public UElementHandler createUastHandler(@NotNull final JavaContext context) {
        return new UElementHandler() {

            @Override
            public void visitCallExpression(@NotNull UCallExpression node) {
                //  checkIsMethod(node);
                checkIsConstructorCall(node);
            }

            private void checkIsConstructorCall(UCallExpression node) {
                try {
                    if (!UastExpressionUtils.isConstructorCall(node)) {
                        return;
                    }
                    UReferenceExpression classRef = node.getClassReference();
                    if (classRef != null) {
                        String className = UastUtils.getQualifiedName(classRef);
                        System.out.println(className);
                        if (className.equals(ALERT_DIALOG_BUILDER) || className.equals(ALERT_DIALOG)) {
                            context.report(ISSUE, node, context.getLocation(node),
                                    "请使用项目提供的BiliCommonDialog");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

       /*     private void checkIsMethod(UCallExpression node) {
                if (UastExpressionUtils.isMethodCall(node)) {
                    if (node.getReceiver() != null && node.getMethodName() != null) {
                        PsiMethod method = node.resolve();
                        if (context.getEvaluator().isMemberInClass(method, WM_ROUTER_CALL)) {
                            context.report(CALL_ISSUE, node, context.getLocation(node),
                                    "请使用项目提供的路由中间件");
                        }
                    }
                }
            }
*/
        };
    }
}
