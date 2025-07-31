# GSON
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn com.google.gson.**
-if class *
-keepclasseswithmembers,allowobfuscation class <1> {
  @com.google.gson.annotations.SerializedName <fields>;
}
-if class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keepclassmembers,allowobfuscation,allowoptimization class <1> {
  <init>();
}

-keep class com.nakersolutionid.nakersolutionid.data.repository.** { *; }
-keep class com.nakersolutionid.nakersolutionid.data.remote.network.** { *; }

-keep class com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource { *; }
-keep class com.nakersolutionid.nakersolutionid.data.remote.RemoteDataMediator { *; }
-keep class com.nakersolutionid.nakersolutionid.data.Resource { *; }