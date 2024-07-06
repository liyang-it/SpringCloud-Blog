# SpringCloud-Blog

一个使用`Spring-Cloud`官方组件开发的微服务博客系统

SpringBoot版本为 `2.7.10`
,对应SpringCloud版本官方文档 <a href="https://docs.spring.io/spring-cloud/docs/2021.0.8/reference/html/ ">
spring-cloud/docs/2021.0.8</a>

## MySQL 与 Redis部署环境

Redis与MySQL都是单机部署

## 目录说明

* **blog-common** `公共模块`
* **blog-register** `注册中心`
* **blog-config** `配置中心`
* **blog-gateway** `网关`
* **blog-admin** `后台管理API`
* **blog-article** `文章博客API`
* **blog-search** `全文搜索、消息队列`

## 后端主要技术栈

* JDK8 `JDK版本`
* MySQL 8 `数据存储`
* MyBatis-PLUS `持久层ORM`
* Redis `缓存实现`
* ElasticSearch `博客文章全文搜索`
* RabbitMQ `消息队列，应用解耦,例如同步数据到ES、点赞、评论`

## SpringCloud使用的组件

* Spring Cloud Eureka `注册中心`
* Spring Cloud Config `配置中心`
* Spring Cloud Gateway `网关统一鉴权,路由转发`
* Spring Cloud OpenFeign `服务之间通信`
* Spring Cloud Hystrix `服务熔断降级`
* spring-cloud-circuitbreaker-resilience4J `服务熔断降级`
* Spring Cloud Ribbon `负载均衡` 

## Web端技术栈

* Vue3

## 启动说明
jar执行必须使用 `JDK8` 否则可能出现无法启动

### 负载均衡部署启动
例如 部署三份 `Blog-Article` 博客文章服务，分别为：
`Blog-Article1`
`Blog-Article2`
`Blog-Article3`

* 首先需要在配置中心创建好 `Blog-Article1.yml` `Blog-Article2.yml` `Blog-Article3.yml` 配置文件，并且使用不同的端口，设置对应的 `instance-id`名称
* 网关配置 Weight负载均衡权重
* 启动 `Blog-Article.jar`程序，并且设置`application.name` 名称，使用名称来选择对应的配置文件信息
* 示例：
```bash
java -jar Blog-Article1.jar --spring.application.name=Blog-Article1
```
执行这个命令 会启动一个 `Blog-Article` 服务 并且 使用远程配置中心的 `Blog-Article1.yml`配置信息




