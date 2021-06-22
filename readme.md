<center><h1>Query-Filter 查询过滤</h1></center>
<center>`Mybatis`拦截器,自动拦截并增强SQL语句</center>

## 这是什么？
这是一个无需任何设置，只需要对`DAO`层的接口添加少许标记即可完成对语句的增强，实现分页、多排序以及多条件过滤功能，并且以上功能均可以在代码中动态生成或者在运行时通过参数进行查找。

## 如何使用？
只需要在你的项目中引入该组件就可以使用，具体做法是在Pom文件中加入如下依赖
```xml
 <dependency>
     <groupId>cn.hll520.queryfilter</groupId>
     <artifactId>query-filter</artifactId>
     <version>1.0.0-release</version>
</dependency>
```

## 是否安全？
在每一次查询前都会对传入的条件和原始语句进行检验，判断是否包含恶意注入和其他非法条件，并对一些方言进行了优化适配（目前仅MySQL)

## 使用步骤
1. 在`DAO`接口方法中加入**参数接口`ITerm`或它的子类或实现了**,列入：
```java
public Student getSTUbyId(ITerm term,Integer id);
```
其中ITerm有多个不同的子接口，分别对应不同的功能，分别如下：
+ `ITermPage`   仅分页
+ `ITermSort`   仅排序
+ `ITermFilter` 仅过滤  
+ `ITermQuery`  全量功能
其分别有`build()`可以构造其实现类，同时`ITerm`接口中也包含了相应方法用来构建不同的实现
  
2. 调用以上接口的对应方法添加条件对象，排序和过滤都支持多个条件

3. 将其实现类传入`DAO`接口即可完成对查询的增强

