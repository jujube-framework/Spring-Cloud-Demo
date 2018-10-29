# 项目启动
- 先启动eureka-server和config-server
- blog-api是二方包
- blog-service是blog-api的实现，是个服务提供方，启动它
- blog-app是消费方，也是对外服务出口，启动它
- blog-gateway是服务网关，通过网关来访问blog-app
- config-server是配置中心，blog-app作为配置客户端。因为要演示动态刷新并读取config-server配置，所以用到rabbitmq，如果您暂时用不到，可以注释掉rabbitmq的相关配置，包括pom.xml中的spring-cloud-starter-bus-amqp和spring配置中的spring.rabbitmq的相关配置(项目中已经暂时关闭了，需要打开请去除注释)
- 断路器的演示在bolg-app和blog-api中，Feign自带了断路器。自定义断路器逻辑的演示在blog-app中，看`hystrix:command:"BlogService#list()":`的相关配置
- zipkin server需要从[latest server](https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec) 下载，然后java -jar zipkin-server.jar即可启动
- admin-server是Spring Boot Admin，用于应用监控

# 访问url
- 注册中心：http://localhost:8761/
- 配置中心示例： http://localhost:8888/foo/dev
- admin-server: http://localhost:8080/
- blog-app熔断器示例（不启动blog-service）：http://localhost:14201/blog/list 。配置文件中把`hystrix:command:"BlogService#list()":fallback:enabled:`改为false可以关闭断路器的fallback,会看到报500错误
- blog-app读取配置中心配置示例：http://localhost:14201/env?key=name
- blog-app重新读取配置中心配置，需要配合rabbitmq使用。用idea的REST Client发送post请求：http://localhost:14201/actuator/bus-refresh ，在控制台可以看到容器刷新并重新读取了配置
- 网关示例，会路由到blog-app：http://localhost:8900/column/env?key=name 或 http://localhost:8900/article/env?key=name
- blog-service和blog-app中配置了zipkin链路追踪，打开zipking界面：http://localhost:9411/
- blog-service中配置了swagger2的演示，访问地址：http://localhost:13201/swagger-ui.html

# 问题备注
- 配置sleuth的时候，blog-app不会发送trace到zipkin，是因为blog-app同时存在web和rabbitmq环境，需要配置sping.zipkin.sender.type=web

# 其他选型
- 配置中心可选携程的Apollo
- 分布式链路追踪可选Skywalking
- 服务监控可选open-falcon