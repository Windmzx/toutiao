#头条资讯网站

使用spring boot mvc redis mybatis mysql等实现一个头条资讯网站
用户可以分享资讯图片和网站连接.可以点赞,踩.

>看了一段spring的入门书籍之后 ，想简单的写个网站试一试。正好看到了一个spring的boot的教程。于是打算先用这个轻量级的框架做一个小网站。
>
>github项目地址[gayhub](https://github.com/Windmzx/toutiao)
>
>项目演示地址[预览](http://toutiao.mengzhexin.com/)
>
>项目的html模板来自牛客网教程，java代码均为本人亲自实现

### start：
spring boot虽然说不需要很多的配置文件，但是基本的引入jar包等工作还是必不可少的。但是springboot提供了一种叫做starter的东西，可以很快的帮你启动项目。
在spring的官网 start.spring.io上有个可以快速生产pom文件的小工具
![](http://image.mengzhexin.com/17-4-16/79121616-file_1492306782653_115b2.png)
我选择了以下的starter
- spring-boot-starter-aop
- spring-boot-starter-velocity
- spring-boot-starter-web
- spring-boot-starter-test
- mybatis-spring-boot-starter
- spring-boot-devtools

其中velocity是用来渲染模版的,以前尝试python的时候就是用的Jinja2,感觉这些模版引擎用起来都差不多.虽然没有jstl强大,都也是都够用的.而且语法也大同小异.
boot-devtools是让你不用重启整个spring应用的情况下,实现更改的预览.
### second
网站主要目的是做一个简单的资讯快速分享网站
具有简单的注册,登陆等功能.
用户可以在网站上分享自己看见的新闻,并附上详细链接,供感兴趣的人,进一步了解.
用户之间可以评论,点赞,点踩.系统会通过异步队列进行发送站内信通知.
表结构如下
![](http://image.mengzhexin.com/17-4-16/9859315-file_1492307657641_9a1a.png)

![](http://image.mengzhexin.com/17-4-16/28141681-file_1492307693844_142c4.png)

![](http://image.mengzhexin.com/17-4-16/58617901-file_1492307722031_1408a.png)

![](http://image.mengzhexin.com/17-4-16/20448788-file_1492307761695_1564b.png)

![](http://image.mengzhexin.com/17-4-16/54553511-file_1492307785746_74e0.png)
### third:
主要功能:
用户上传的图片上传到七牛云,避免了本地存放文件可能导致的丢失等问题.对文件进行简单的验证,不允许上传非图片.(后期改进事项:识别完用户上传文件类型之后,应该也是用异步队列上传七牛云)

用户登陆之后在本地cookie里放置一个ticket字段,维护用户的登陆状态,避免用户需要重复登陆.

使用拦截器intercepter在请求里加上用户的身份.可以在一次请求中多处使用.

简单练习一下aop技术,在关键操作上进行增强记录用户上传的数据,打印出log

使用异步队列,点赞,踩,评论等,都使用异步队列进行通知等操作.异步队列主要是用生产者-消费者模型.构建一个事件模型,存放发生的事件现场. 在spring初始化的时候,在消费者class中实现InitializingBean和InitializingBean. 从spring的容器中所有取出用注解注册的事件处理类Eventhandler.并把每个Eventhandle监听的事件Eventtype,映射到每个Eventtype应该调用哪一些handler这个map上.

这样,消费者每次取出生产者放入缓存队列中的事件.就去查询map中对应的handler集合.并依次调用.消费者队列使用redis内存数据库实现.
### 截图
![](http://image.mengzhexin.com/17-4-16/71592846-file_1492310160345_c846.png)

![](http://image.mengzhexin.com/17-4-16/92929990-file_1492309956959_124ae.png)

![](http://image.mengzhexin.com/17-4-16/49500533-file_1492310196860_7267.png)

![](http://image.mengzhexin.com/17-4-16/48909244-file_1492310235568_c6ee.png)

![](http://image.mengzhexin.com/17-4-16/7649127-file_1492310265007_176f6.png)

登陆后可以赞踩
![](http://image.mengzhexin.com/17-4-16/1013251-file_1492310308039_e8e7.png)
需要在application.properties添加以下配置
```aidl
spring.datasource.url=jdbc:mysql://localhost:3306/toutiao?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
mybatis.config-location=classpath:mybatis-config.xml
logging.level.root=ERROR
spring.velocity.suffix=.html
spring.velocity.cache=false
spring.velocity.toolbox-config-location=toolbox.xml
multipart.max-file-size=10Mb
accessKey=[七牛云]
secretKey=[七牛云]
bucket=spring
key=1
```