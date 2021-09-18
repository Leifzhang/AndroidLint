package com.kornos.lint.demo.privacy

import org.objectweb.asm.Opcodes

/**
 *
 *  @Author LiABao
 *  @Since 2021/9/2
 *
 */
object PrivacyHelper {

    val privacyList = mutableListOf<PrivacyAsmEntity>().apply {
        add(
            PrivacyAsmEntity(
                Opcodes.INVOKEVIRTUAL, "android/telephony/TelephonyManager",
                "getDeviceId", "()Ljava/lang/String;"
            )

        )
        add(
            PrivacyAsmEntity(
                Opcodes.INVOKEVIRTUAL, "android/net/wifi/WifiInfo",
                "getSSID", "()Ljava/lang/String;"
            )
        )
        add(
            PrivacyAsmEntity(
                Opcodes.INVOKEVIRTUAL, "android/net/wifi/WifiInfo",
                "getBSSID", "()Ljava/lang/String;"
            )
        )
        add(
            PrivacyAsmEntity(
                Opcodes.INVOKESTATIC,
                "android/provider/Settings\$Secure",
                "getString",
                "(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;"
            )
        )
        add(
            PrivacyAsmEntity(
                Opcodes.INVOKEVIRTUAL, "android/content/pm/PackageManager",
                "getInstalledPackages", "(I)Ljava/util/List;"
            )
        )

        add(
            PrivacyAsmEntity(
                Opcodes.INVOKEVIRTUAL, "android/content/pm/PackageManager",
                "getInstalledApplications", "(I)Ljava/util/List;"
            )
        )


    }


    val whiteList = mutableListOf("com/wallstreetcn", "leakcanary", "shark").apply {

    }
}

