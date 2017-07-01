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