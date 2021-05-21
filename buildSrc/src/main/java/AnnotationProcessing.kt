package dependencies

object AnnotationProcessing {
    val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle_version}"
    val hilt_google_compiler =  "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
    val hilt_compiler =  "androidx.hilt:hilt-compiler:${Versions.hiltVersion}"
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
}