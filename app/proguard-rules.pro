-keepattributes Signature
-keepattributes *Annotation*

-keep class com.myai.smartreplyai.data.model.** { *; }
-keep class com.myai.smartreplyai.features.ai.data.** { *; }

-dontwarn okhttp3.**
-dontwarn retrofit2.**
