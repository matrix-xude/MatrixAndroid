# SideEffect 附带效应
side-effect 附带效应，是指@composable函数中感知作用域外的生命周期变化，因为重组是随时发生、取消、不可预测。所以理论上composable方法中不应该有附带效应。
但是在某些需求中，确实需要知道外部生命周期的变化。如：在onStart中注册监听，onStop中移除监听。这就必须用到 side-effect API。

这里就需要用到LaunchedEffect,DisposableEffect等代码