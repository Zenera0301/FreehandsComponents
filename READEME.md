使用本插件的步骤：

1. 新建AS工程；

2. 在项目的build.gradle中：
```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

3. 在app模块中：
```groovy
dependencies {
    ...
    implementation 'com.github.Zenera0301:FreehandsComponents:0.4.0'
}
```

4. 最后，在MainActivity中跳转Case_MainActivity
```java
Intent intent = new Intent(this, Case_MainActivity.class);
startActivity(intent);
```