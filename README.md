# moon
参考携程 apollo 实现的一款前后端分离的配置中心

### 使用方法
1、在 pom 中引入依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.moon</groupId>
        <artifactId>moon-client-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>maven-nexus</id>
        <name>maven-nexus</name>
        <url>http://www.rabbit-cloud.top:8081/repository/maven-public/</url>
    </repository>
</repositories>
```

2、在项目配置文件中配置必要参数
```properties
# 应用id，代表一个微服务
moon.appid=example
# 配置中心地址
moon.host=www.rabbit-cloud.top
```

3、进入web添加app，修改配置，快去体验吧
http://www.rabbit-cloud.top