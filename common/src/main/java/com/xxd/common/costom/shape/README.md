## Shape xml文件代码化
shape的xml文件通用性很低，圆角弧度，边框宽度、颜色等变动都需要重新写一个xml文件

这个通用的shape可以解决3类问题
1. 绘制带shape的控件，可自定义圆角弧度，边框颜色、宽度，填充颜色等
2. 绘制带按压效果（Pressed状态、Normal状态）的控件，与上面相同，按压后的圆角弧度，填充颜色、宽度，填充颜色等都可以直接配置，
同时支持在Normal状态的基础上直接设置一个颜色的透明度变化（如：pressed状态颜色变为原来颜色80%透明）
3. 代替普通select效果，select,unselect可以直接设置2个图片

## 例子
1. 一个带圆角的ViewGroup
上边左右加上10dp的圆角弧度，下边不变
```xml
<com.kongfz.app.core.common.drive.custom.shape.CustomShapeConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:CustomShapeFillColor="@color/white"
    app:CustomShapeRadiusTopLeft="@dimen/dp_10"
    app:CustomShapeRadiusTopRight="@dimen/dp_10"/>
```

2. 一个简单的"领取"按钮，背景红色，带圆角
```xml
<com.kongfz.app.core.common.drive.custom.shape.CustomShapeSimpleTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="领取"
    app:CustomShapeFillColor="@color/red_1"
    app:CustomShapeRadius="@dimen/dp_20" />
```

3. pressed状态下，颜色等进行变化的控件
```xml
<com.kongfz.app.core.common.drive.custom.shape.CustomShapeTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:CustomShapeCanPress="true" 
    app:CustomShapeFillColor="@color/red_1"
    app:CustomShapeRadius="4dp"
    app:CustomShapeTransparencyScale="0.7"
/>
```
名词解释：
app:CustomShapeCanPress="true" ： 按压显示不同状态的开关
app:CustomShapeFillColor="@color/red_1" ： normal状态下的图片背景
app:CustomShapeRadius="4dp"  ： normal状态下图片的圆角
app:CustomShapeTransparencyScale="0.7" ： pressed状态下，在原色的基础上去70%透明度
其它：pressed状态下的其它数据，比如圆角弧度等，如果没有新设置，会使用normal状态下的数据

