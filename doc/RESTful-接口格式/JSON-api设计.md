# BSON-API-STARTER

参照[JSON-API][https://jsonapi.org/ ]的RESTful风格API响应的实现。

本项目目前只适用于Spring Boot项目。

`Bravo-mall`项目的服务间通信以及服务对外网的API响应皆遵守该协议。

## 文档

### 安装

#### Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>bson-api-starter</artifactId>
    <version>0.0.2</version>
</dependency>
```

### 使用

> 若您的项目不使用自定义异常做业务流程控制，则无需第3、4步，但本项目则不会生成errors字段。

1. 创建一个控制器返回参数拦截类并继承`BsonApiWrapperResponseAdvice`
2. 为该类添加上注解`@RestControllerAdvice`
3. 创建一个全局异常拦截类并继承`BsonApiGlobalExceptionHandler`
4. 为该类添加上注解`@RestControllerAdvice`
5. 就这么简单。



### 包结构

#### body包

该包下各实体类对应完整的[`Bson-api`响应体。](#格式)

#### starter包

该包是`Spring Boot Starter`的实现模块，用于实现本项目的开箱即用功能，方便整合到Spring Boot项目中。

#### strategy包

策略包，该包下目前是根据控制层返回参数生成[`data`](#资源字段`data`)字段的实现类。

#### vo包

视图包，对于该报目前只有一个接口，用于规范控制层的返回参数。

##### exception包

自定义异常包，本项目基于自定义异常生成[errors](#错误字段`errors`)字段。

#### advice包

全局拦截器包，该包下有全局异常处理器和控制层响应拦截器，用于实现统一响应体。



## 规定

#### 程序规定

- 统一使用继承于`BsonApiException`类的__自定义异常进行异常处理__。
- 规定`data`和`errors`具有互斥性，一个响应体中不会同时出现。
- 控制层返回单个资源必须是`BsonApiDataItem`接口的实现类。
- 控制层返回多个资源必须是` Collection<T extends BsonApiDataItem>`的实现类。





# 格式

## 资源字段`data`

规定无论服务器返回多少个资源，data字段始终是一个列表`List`。

### 成员

- type - 描述资源的类型，如 article、user、commodity
- id - 该资源的唯一标识
- attributes - 该资源的属性值
- relationships - 与该资源相关的资源

### 例子

#### 被请求的资源有相关资源`relationships`

```json
{
    "data": [
        {
            "type": "articles",
            "id": "1",
            "attributes": {
                "title": "Rails is Omakase"
            },
            "relationships": {
                "author": {
                    "links": {
                        "self": "http://example.com/articles/1/relationships/author",
                        "related": "http://example.com/articles/1/author"
                    },
                    "data": {
                        "type": "people",
                        "id": "9"
                    }
                }
            },
            "links": {
                "self": "http://example.com/articles/1"
            }
        }
    ]
}
```



## 错误字段`errors`

当遇到一个问题时,服务器可以选择停止处理,也可以继续处理,遇到多个问题。 比如,服务器可能处理多个属性,并在一个响应中返回多个验证问题。

当服务器在一个请求中遇到多个问题,在响应中应该使用HTTP最通用的错误状态码。 比如,`400 Bad Request`可被用于多个4xx错误,`500 Internal Server Error`可被用于多个5xx错误。

### 成员

- id - 特定问题的唯一标识符。
- status - 适用于这个问题的HTTP状态码，使用字符串表示。
- code - 应用特定的错误码，以字符串表示。
- title - 简短的，可读性高的问题总结。除了国际化本地化处理之外，不同场景下，相同的问题，值是不应该变动的。
- detail - 针对该问题的高可读性解释。与`title`相同,这个值可以被本地化。
- source - 包括错误资源的引用的对象。它也可以包括以下任意成员:
  - pointer - 一个指向请求文档中相关实体的`JSON Pointer`。
  - parameter - 指出引起错误的查询对象的字符串。

例子

单个错误

```json
HTTP/1.1 422 Unprocessable Entity
Content-Type: application/vnd.api+json

{
  "errors": [
    {
      "status": "422",
      "source": { "pointer": "/data/attributes/firstName" },
      "title":  "Invalid Attribute",
      "detail": "First name must contain at least three characters."
    }
  ]
}
```

#### 多个错误

```json
HTTP/1.1 400 Bad Request
Content-Type: application/vnd.api+json

{
  "errors": [
    {
      "status": "403",
      "source": { "pointer": "/data/attributes/secretPowers" },
      "detail": "Editing secret powers is not authorized on Sundays."
    },
    {
      "status": "422",
      "source": { "pointer": "/data/attributes/volume" },
      "detail": "Volume does not, in fact, go to 11."
    },
    {
      "status": "500",
      "source": { "pointer": "/data/attributes/reputation" },
      "title": "The backend responded with an error",
      "detail": "Reputation service not responding after three requests."
    }
  ]
}
```

```json
HTTP/1.1 422 Unprocessable Entity
Content-Type: application/vnd.api+json

{
  "jsonapi": { "version": "1.0" },
  "errors": [
    {
      "code":   "123",
      "source": { "pointer": "/data/attributes/firstName" },
      "title":  "Value is too short",
      "detail": "First name must contain at least three characters."
    },
    {
      "code":   "225",
      "source": { "pointer": "/data/attributes/password" },
      "title": "Passwords must contain a letter, number, and punctuation character.",
      "detail": "The password provided is missing a punctuation character."
    },
    {
      "code":   "226",
      "source": { "pointer": "/data/attributes/password" },
      "title": "Password and password confirmation do not match."
    }
  ]
}
```

## 元信息字段`meta`

### 成员

- copyright - 版权信息
- authors - 作者列表

### 例子

```json
{
  "data": {
    // ...
  },
  "meta": {
    "copyright": "Copyright 2015 Example Corp.",
    "authors": [
      "Yehuda Katz",
      "Steve Klabnik",
      "Dan Gebhardt",
      "Tyler Kellen"
    ]
  }
}
```

## 服务器响应状态码

### 200 OK `成功返回资源`

如果服务器通过请求成功获取一个单独资源或资源集合，服务器必须以`200 OK`状态码回复。

如果服务器通过请求成功获取一个资源集合，服务器必须将一个[资源对象](http://jsonapi.org/format/#document-resource-objects)的列表或空列表`[]`作为响应文档的主数据。

### 201 Created `成功创建资源，响应中已返回`

如果`POST`请求不包括客户端生成的ID,并且请求的资源成功被创建,服务器必须返回`201 Created`状态码。

响应的`data`字段中必须有刚才所创建的资源对象。

### 202 Accepted `请求未处理完成`

请求被服务器接受，但在响应时服务器并未处理完成。

### 204 No Content `成功创建资源，但响应中未返回`

如果`POST`请求包括客户端生成的ID,并且请求的资源成功被创建,那么服务器必须返回`201 Created`状态码和响应文档(如上所述), ,或者只返回`204 No Content`状态码,没有响应文档。

### 400 Bad Request `请求参数不合法`

API接收到的请求参数不合法。

### 401 Unauthorized `客户端身份未认证或权限不足`

客户端未登录，无登录凭证或者该登录凭证所具有的权限不足。

### 403 Forbidden `拒绝该请求`

服务器可能向不支持的创建资源的请求返回`403 Forbidden`的响应。

### 404 Not Found `请求的资源不存在`

当通过一个请求获取一个不存在的单独资源时，服务器必须以`404 Not Found`状态码回复。

### 409 Conflict `请求创建的资源已经存在 或 服务器不存在该资源类型`

在创建资源请求`POST`中，客户端所给的`id`已经存在或者`type`不在服务器接受的范围。

