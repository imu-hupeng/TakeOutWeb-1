# TakeOutWeb的API说明(v1)
## 餐馆端
### 检查更新
#### 地址：/v1/restaurant/check_version
#### 参数：无
#### 成功返回结果样例：
```json
{
  "ret": 0,
  "msg": "检查成功",
  "data": {
    "id": 1,
    "versionCode": 1,
    "versionName": "1.0",
    "url": "http://www.baidu.com/1.apk",
    "forced": 1,
    "versionDescription": "此版本修复了一些bug",
    "addTime": "2017-07-01 11:44:01"
  }
}
```
#### 失败返回结果样例：
```json
{
  "ret": -1,
  "msg": "检查失败，服务器上不包含任何版本的信息"
}
```
#### 返回码含义:
| 返回码        | 含义   |
| :----: | :----:  |
|  0     | 检查成功，并返回最新的版本的信息   |
| -1     | 检查失败，服务器上无任何可用版本   |

<br>

### 用户登录
#### 地址：/v1/restaurant/login
#### 参数：username=[username or phone]&password=[password]
#### 成功返回样例
```json
{
  "ret": 0,
  "msg": "登录成功",
  "data": {
    "username": "admin",
    "phone": "18847163389",
    "ak": "5c2016efc3d17c558b4788a153d3084889148fbb83451fe596bbb231b65780c275bbaafe8f7964257caf0726f2047a49"
  }
}
```
#### 失败返回样例
```json
{
  "ret": -1,
  "msg": "登录失败,原因：用户名或者密码错误",
  "data": null
}
```
#### 返回码含义:
| 返回码        | 含义   |
| :----: | :----:  |
|  0     | 登录成功，获得用户的信息   |
| -1     | 登录失败   |

<br>

### 检查用户的登录状态
#### 地址：/v1/restaurant/check_login_status
#### 参数：ak=[登录返回的ak值]
#### 成功返回的样例：
```json
{
  "ret": 0,
  "msg": "当前登录状态有效",
  "data": {
    "username": "admin",
    "phone": "18847163389",
    "ak": "b46700bb4a603b12c09433aa400198823b99ae5a10fd3c8964f1bb3e4942d7fbefb8abad454bfb45dabe75def4a10d80"
  }
}
```
#### 失败返回的样例：
```json
{
  "ret": -1,
  "msg": "当前登录状态无效",
  "data": null
}
```
#### 返回码含义:
| 返回码        | 含义   |
| :----: | :----:  |
|  0     | 当前登录状态有效   |
| -1     | 当前登录状态无效   |

<br>