# java-repo-240405312

Java 面向对象编程实验 —— 校园导航系统

## 项目简介

基于封装、继承、多态、抽象类实现的校园导航系统（Lab Practice 2）。

## 运行

```bash
javac -encoding UTF-8 -d out src/campusnav/*.java
java -cp out campusnav.Main
```

## 测试

```bash
javac -encoding UTF-8 -cp "out;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" -d out-test test/campusnav/*.java
java -cp "out;out-test;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore campusnav.NavigationSystemTest
```
