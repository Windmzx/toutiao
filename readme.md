#头条资讯网站

使用spring boot mvc redis mybatis mysql等实现一个头条资讯网站
用户可以分享资讯图片和网站连接.可以点赞,踩.
[点击预览](http://toutiao.mengzhexin.com)
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
