/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)
 Source Host           : localhost:3306
 Source Schema         : spring_cloud_blog

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 12/07/2024 13:57:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '0正常 1 禁用',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `created_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `updated_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '0 正常 1 删除 ',
  `deleted_time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_username`(`username` ASC, `deleted` ASC, `deleted_time` ASC) USING BTREE COMMENT '用户名唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1805798658228178946', 'liyang2', '123456', 0, '2024-06-26 11:02:05', 'liyang', NULL, 'liyang', 1, '2024-06-26T11:23:28.833');
INSERT INTO `t_admin` VALUES ('1805841157210583042', 'liyang3', '123456', 0, '2024-06-26 13:50:57', 'liyang', NULL, NULL, 0, '-');
INSERT INTO `t_admin` VALUES ('2c12846332d411efa19798eecbb88c8b', 'liyang', '123456', 0, '2024-06-25 17:20:54', NULL, NULL, NULL, 0, '-');

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `created_time` datetime NULL DEFAULT NULL,
  `created_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  `updated_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `version` int NULL DEFAULT 1 COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '0正常 1 删除',
  `deleted_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客文章信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES ('1809061017768235009', 'Java', '阿西吧', '2024-07-05 11:05:32', 'liyang', '2024-07-05 17:30:35', 'liyang', 8, 0, NULL);
INSERT INTO `t_article` VALUES ('1809061029810081793', 'Java', '你好啊', '2024-07-05 11:05:35', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810118642369200130', 'Java', 'Java 数据类型 -- Java 语言的 8 种基本数据类型、字符串与数组', '2024-07-08 09:08:09', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810118782773526529', 'Java', 'Java高阶数据结构-----并查集（详解）', '2024-07-08 09:08:43', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810118867959840769', 'Java', '第13章 基于Java Swing的图书管理系统', '2024-07-08 09:09:03', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810118944648495106', 'Java', 'Java 反射机制 -- Java 语言反射的概述、核心类与高级应用', '2024-07-08 09:09:21', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810119219920666626', 'Java', 'SpringBootWeb 篇-深入了解 Redis 五种类型命令与如何在 Java 中操作 Redis', '2024-07-08 09:10:27', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810119301722177538', 'Java', 'Java——面向对象进阶（一）', '2024-07-08 09:10:47', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810119370408099841', 'Java', '深入探讨Java中的对象拷贝：浅拷贝与深拷贝的差异与应用', '2024-07-08 09:11:03', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810119447977558017', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 09:11:21', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810134115832033282', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 10:09:39', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810156667828588545', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 11:39:15', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810157394537893889', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 11:42:09', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810157398522482690', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 11:42:10', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810159074620887042', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 11:48:49', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810160311957381122', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 11:53:44', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810192593367764994', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 14:02:01', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810193036617617409', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 14:03:46', 'liyang', NULL, NULL, 1, 0, NULL);
INSERT INTO `t_article` VALUES ('1810193519432339458', 'Java', '【Java】还不懂this关键字？一分钟彻底弄懂this关键字', '2024-07-08 14:05:41', 'liyang', NULL, NULL, 1, 0, NULL);

-- ----------------------------
-- Table structure for t_article_class
-- ----------------------------
DROP TABLE IF EXISTS `t_article_class`;
CREATE TABLE `t_article_class`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `created_time` datetime NULL DEFAULT NULL,
  `created_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  `updated_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '0正常 1 删除',
  `deleted_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_class_name`(`class_name` ASC, `deleted` ASC, `deleted_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客文章分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_class
-- ----------------------------
INSERT INTO `t_article_class` VALUES ('1qweqwe', 'springCloud', '2024-06-28 11:08:36', 'liyang', '2024-06-28 11:52:57', 'liyang', 1, '2024-06-28T11:52:56.946');
INSERT INTO `t_article_class` VALUES ('9268966185de4df89d8f59ec8534c8e9', 'Java2', '2024-06-28 11:49:46', 'liyang', NULL, NULL, 0, '-');
INSERT INTO `t_article_class` VALUES ('abd66d8a040340dbb794e14ee7f27e03', 'Java', '2024-06-28 11:49:40', 'liyang', NULL, NULL, 0, '-');
INSERT INTO `t_article_class` VALUES ('qwewqewq', 'Java1232', '2024-06-28 11:08:02', 'liyang', '2024-06-28 11:52:42', 'liyang', 1, '2024-06-28T11:52:41.651');

-- ----------------------------
-- Table structure for t_article_content
-- ----------------------------
DROP TABLE IF EXISTS `t_article_content`;
CREATE TABLE `t_article_content`  (
  `article_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客文章信息内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_article_content
-- ----------------------------
INSERT INTO `t_article_content` VALUES ('1809061017768235009', '修改内容');
INSERT INTO `t_article_content` VALUES ('1809061029810081793', '# 1、创建需要使用的工具类 文件名 `ByteConverter.java`  用于将字节数值转为 MB数值 ```java import java.math.BigDecimal; import java.math.RoundingMode; /** * <h2>字节数值转换</h2> * <p> * * </p> * * @author 作者<1922802352@qq.com> * @since 2023年01月12日 下午4:14:02 **/ public class ByteConverter { /** * 将字节数量转换为MB * * @param bytes 字节数量 * @return 对应的MB */ public static BigDecimal bytesToMB(long bytes) { BigDecimal bytesConver = BigDecimal.valueOf(bytes); // 字节数量转换MB = 字节数 / (1024 * 1024) BigDecimal conditions = BigDecimal.valueOf(1048576L); return bytesConver.divide(conditions, 2, RoundingMode.HALF_UP); } } ``` 可选 有些地方使用到了 `hutool`工具类，在代码中会有注释标注哪些地方使用，不使用hutool工具也可以，但是需要自己实现特定工具 ```xml <dependency> <groupId>cn.hutool</groupId> <artifactId>hutool-all</artifactId> <version>5.8.16</version> </dependency> ``` # 2、创建一个 jvm信息对象类 ```java import cn.hutool.core.date.DateUtil; import cn.hutool.core.lang.Singleton; import java.lang.management.ManagementFactory; import java.lang.management.RuntimeMXBean; import java.math.BigDecimal; import java.math.RoundingMode; import java.time.Instant; import java.time.LocalDateTime; import java.time.ZoneId; /** * <h2>JVM运行信息</h2> * <p> * * </p> * * @author 作者<1922802352@qq.com> * @since 2023年01月12日 下午4:14:02 **/ public class JvmInfo { /** * 单例模式，使用 hutool工具，也可以自己实现，也可以直接不用单例模式 直接new */ private static JvmInfo info = Singleton.get(JvmInfo.class); /** * JVM已用内存 */ private BigDecimal usedMemory; /** * JVM最大可用内存 */ private BigDecimal maxMemory; /** * JVM剩余内存 */ private BigDecimal freeMemory; /** * jdk安装路径 */ private String jdkHome; /** * jdk版本 */ private String jdkVersion; /** * jdk名称 */ private String jdkName; /** * 程序启动时间 */ private String startTime; /** * 已运行时间 */ private String elapsedTime; /** * 运行参数 */ private String runParamters; /** * JVM内存使用率 */ private BigDecimal usage; private JvmInfo(){} public static JvmInfo getInstance() { MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean(); MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage(); RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean(); // jvm最大可用内存 info.setMaxMemory(ByteConverter.bytesToMB(heapMemoryUsage.getMax())); // jvm已使用内存 info.setUsedMemory(ByteConverter.bytesToMB(heapMemoryUsage.getUsed())); // jvm剩余内存 info.setFreeMemory(info.getMaxMemory().subtract(info.getUsedMemory())); // jvm内存使用率 info.setUsage(info.getUsedMemory().divide(info.getMaxMemory()');
INSERT INTO `t_article_content` VALUES ('1810118642369200130', '大家好，我是栗筝i，这篇文章是我的 “栗筝i 的 Java 技术栈” 专栏的第 004 篇文章，在 “栗筝i 的 Java 技术栈” 这个专栏中我会持续为大家更新 Java 技术相关全套技术栈内容。专栏的主要目标是已经有一定 Java 开发经验，并希望进一步完善自己对整个 Java 技术体系来充实自己的技术栈的同学。与此同时，本专栏的所有文章，也都会准备充足的代码示例和完善的知识点梳理，因此也十分适合零基础的小白和要准备工作面试的同学学习。当然，我也会在必要的时候进行相关技术深度的技术解读，相信即使是拥有多年 Java 开发经验的从业者和大佬们也会有所收获并找到乐趣。');
INSERT INTO `t_article_content` VALUES ('1810118782773526529', '并查集概念：将n个不同的元素划分成一些不相交的集合。开始时，每个元素自成一个单元素集合，然后按一定的规律将归于同一组元素的集合合并。在此过程中要反复用到查询某一个元素归属于那个集合的运算。适合于描述这类问题的抽象数据类型称为并查集(union-ﬁnd set)。');
INSERT INTO `t_article_content` VALUES ('1810118867959840769', '在当今社会，随着信息技术的不断发展，信息管理系统已经进入到了人类社会的各个领域，人们对于信息技术的掌握也越来越迅速。在图书管理的过程中也引入图书管理体系，图书管理系统将大大节省人力、物力、时间、金钱等资源，不仅方便了工作人员的管理，也增加了读者查找、借阅图书的便利。');
INSERT INTO `t_article_content` VALUES ('1810118944648495106', 'Reflection（反射）是被视为动态语言的关键，反射机制允许程序在执行期借助于 ReflectionAPI 取得任何类的内部信息，并能直接操作任意对象的内 部属性及方法。');
INSERT INTO `t_article_content` VALUES ('1810119219920666626', ' Redis 是一个基于内存的 key-value 结构数据库。基于内存存储，读写性能高。适合存储热点数据，以便大量用户访问时可以快速得到响应。');
INSERT INTO `t_article_content` VALUES ('1810119301722177538', '在 Java 中，static 关键字用于声明类中的静态成员（包括字段、方法、内部类和代码块）。静态成员属于类本身，而不是类的实例。');
INSERT INTO `t_article_content` VALUES ('1810119370408099841', '前言：在Java编程中，深拷贝（Deep Copy）与浅拷贝（Shallow Copy）是两个非常重要的概念。它们涉及到对象在内存中的复制方式，对于理解对象的引用、内存管理以及数据安全都至关重要。');
INSERT INTO `t_article_content` VALUES ('1810119447977558017', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810134115832033282', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810156667828588545', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810157394537893889', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810157398522482690', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810159074620887042', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810160311957381122', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810192593367764994', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810193036617617409', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');
INSERT INTO `t_article_content` VALUES ('1810193519432339458', '在上一篇【JavaSE】一文看懂构造器/构造方法（Cunstructor）中，我们已经在构造器中初步窥得this关键字的冰山一角了。大家有没有考虑过，在一个类的构造器中，我们为什么要使用this.属性而不直接使用属性呢？毕竟一个类中，不管属性被什么关键字修饰，在类中都可以访问。随着这个问题，今天瑶瑶子带大家深入分析一下Java中this关键字,让大家彻底理解this关键字.');

-- ----------------------------
-- Table structure for t_queue_consumption_failure
-- ----------------------------
DROP TABLE IF EXISTS `t_queue_consumption_failure`;
CREATE TABLE `t_queue_consumption_failure`  (
  `message_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息id',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `queue_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '队列名称',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态 0(失败,默认值) 1 重新处理成功',
  `created_time` datetime NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息队列消费失败记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_queue_consumption_failure
-- ----------------------------

-- ----------------------------
-- Table structure for t_queue_send_failure
-- ----------------------------
DROP TABLE IF EXISTS `t_queue_send_failure`;
CREATE TABLE `t_queue_send_failure`  (
  `message_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息id',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `queue_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '队列名称',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态 0(失败,默认值) 1 重新处理成功',
  `created_time` datetime NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息发送失败记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_queue_send_failure
-- ----------------------------

-- ----------------------------
-- Table structure for t_system
-- ----------------------------
DROP TABLE IF EXISTS `t_system`;
CREATE TABLE `t_system`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码值',
  `label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_code`(`code` ASC, `label` ASC, `value` ASC) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_system
-- ----------------------------
INSERT INTO `t_system` VALUES (1, 'applicationLogoUrl', 'applicationLogoUrl', 'https://springdoc.cn/logo-512.webp', '程序logo图片');
INSERT INTO `t_system` VALUES (2, 'applicationName', 'applicationName', '小菊', '程序名称');
INSERT INTO `t_system` VALUES (3, 'applicationTitle', 'applicationTitle', 'XiaoJu', '程序名称标题');

SET FOREIGN_KEY_CHECKS = 1;
