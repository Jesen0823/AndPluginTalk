# AndPluginTalk
android plugin 总结

### 占位式

   插件是未安装的apk, 在宿主中设定代理Activity,用来启动插件，就叫做**占位** 或 **插桩**。

    插件没有组件的环境，无法独立运行，所以要把宿主的运行环境给插件。这就需要标准来传递。


#### 占位式Activity通信


    ![activity占位插桩效果](./images/README-1630744851282.gif)

    ![插桩Activity原理](./images/README-1630745575224.png)

    ![加载插件apk](./images/README-1630745613420.png)



