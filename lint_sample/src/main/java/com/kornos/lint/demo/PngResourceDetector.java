package com.kornos.lint.demo;

import com.android.resources.ResourceFolderType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.ResourceContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class PngResourceDetector extends Detector implements Detector.ResourceFolderScanner {

    public static final Issue ISSUE = Issue.create(
            "image too large",
            "Log Usage",
            "Please use the unified LogUtil class!",
            Category.CORRECTNESS,
            6,
            Severity.ERROR,
            new Implementation(PngResourceDetector.class, Scope.RESOURCE_FOLDER_SCOPE)
    );


    @Override
    public boolean appliesToResourceRefs() {
        return super.appliesToResourceRefs();
    }


    @Override
    public void checkFolder(@NotNull ResourceContext context, @NotNull String folderName) {
        super.checkFolder(context, folderName);
        File parent = context.file;
        for (File file : parent.listFiles()) {
            if (file.isFile()) {
                long length = file.length();
                if (length > 10) {
                    System.out.print(file.toString() + "\n");
                    context.report(ISSUE, Location.create(file),
                            "This code mentions `lint`: **Congratulations**");
                }
            }
        }
    }

    @Override
    public boolean appliesTo(@NotNull ResourceFolderType folderType) {
        System.out.print(folderType.name());
        // return true;
        return folderType.compareTo(ResourceFolderType.DRAWABLE) == 0 || folderType.compareTo(ResourceFolderType.MIPMAP) == 0;
    }
}
