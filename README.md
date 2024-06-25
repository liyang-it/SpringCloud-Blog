# SpringCloud-Blog

一个使用`Spring-Cloud`官方组件开发的微服务博客系统

SpringBoot版本为 `2.6.15`,对应SpringCloud版本官方文档 <a href="https://docs.spring.io/spring-cloud/docs/2021.0.9/reference/htmlsingle/">spring-cloud/docs/2021.0.9</a>

## 后端主要技术栈
* JDK8 `JDK版本`
* MySQL 8 `数据存储`
* Redis `缓存实现,分布式锁`
* ElasticSearch `博客文章搜索`
* MyBatis-PLUS `持久层ORM`
* RabbitMQ `消息队列，应用解耦,例如点赞、评论`

## SpringCloud使用的组件
* Spring Cloud Eureka `注册中心`
* Spring Cloud Config `配置中心`
* Spring Cloud Gateway `网关统一鉴权,路由转发`
* Spring Cloud OpenFeign `服务之间通信`
* Spring Cloud Hystrix `服务熔断，服务降级`
* Spring Cloud Ribbon `负载均衡`

## Web端技术栈

* Vue3
