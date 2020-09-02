## 插件使用
1. 在项目根目录的build.gradle里依赖AspectJX
  ```
  dependencies {
    classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10'
    // 如果build:gradle在3.6.0以下，使用2.0.8版本
  }
  ```
2. 在app目录的build.gradle里添加Aspectj的插件
  `apply plugin: 'android-aspectjx'`
3. 在app目录的build.gradle里去掉不必要侵入的类
    不去掉可能会导致 java.util.zip.ZipException: zip file is empty 或者 classNotDefFoundException
    ```
    aspectjx{
        exclude 'com.google','com.squareup','com.alipay','org.apache'
    }
    ```
4. 如果你使用得分组件时androidx版本则用master分支，否则使用support分支
## 文章介绍
