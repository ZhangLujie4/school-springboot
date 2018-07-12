# 教师
```http request
POST /teacher/login
```

参数
```text
username: "100026810001"
password: "888888"

```

返回
```json
{
  "code": 1,
  "msg": "教师登录成功",
  "data": [
    {
      "id": "100026810001",
      "name": "张三",
      "sex": "男",
      "birth": "12.30",
      "classId": 1,
      "classNum": "04",
      "major": "软件工程",
      "year": 2000,
      "institute": "计算机学院",
      "phone": "",
      "email": ""
    }
  ]  
}
```

```http request
GET /teacher/lessons/100026810001
```

参数
```text
无
```

返回
```json
{
  "code": 0,
  "msg": "成功",
  "data":[
    {
      "id": 1,
      "classId": 1,
      "time": "周一上午",
      "place": "操场",
      "teacher": "张三",
      "name": "体育"
    },
    {
      "id": 2,
      "classId": 1,
      "time": "周一上午",
      "place": "操场",
      "teacher": "张三",
      "name": "体育"
    }
  ]
}
```

```http request
POST /teacher/password
```

参数
```text
username: "100026810001"
passsword: "888888"
newPassword: "666666"
```

返回
```json
{
  "code": 0,
  "msg": "成功",
  "data": null
}
```


```http request
POST /teacher/message
```

参数
```text
classId: 1
sender: "201526810426"
receiver: "100026810001"
message: "nice to meet you!!"
```

返回
```json
{
  "code": 0,
  "msg": "成功",
  "data": null
}
```

# 学生
```http request
POST /student/login
```

参数
```text
username: "201526810426"
password: "888888"
```

返回
```json
{
  "code": 2,
  "msg": "学生登录成功",
  "data": [
    {
      "id": "201526810426",
      "name": "张璐杰",
      "sex": "男",
      "birth": "12.30",
      "classId": 1,
      "classNum": "04",
      "index": "26",
      "major": "软件工程",
      "year": 2015,
      "institute": "计算机学院"
    }
  ]  
}
```

# 管理员
```http request
POST /admin/login
```

参数
```text
username: "0001"
password: "888888"
```

返回
```json
{
  "code": 3,
  "msg": "管理员登录成功",
  "data": [
    {
      "id": "0001"
    }
  ]
}

```
