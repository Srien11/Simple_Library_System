# 图书管理系统 API 文档

## 1. 文档说明

### 1.1 基本信息
- **项目名称**：图书管理系统（BookManager）
- **API版本**：v1.0
- **基础URL**：`http://localhost:8092/BookManager`
- **数据格式**：JSON
- **字符编码**：UTF-8

### 1.2 通用响应格式

#### 操作结果响应
```json
{
    "status": 200,
    "message": "操作成功",
    "data": {},
    "timestamp": 1234567890
}
```

#### 分页查询响应
```json
{
    "code": 0,
    "message": "success",
    "count": 100,
    "data": []
}
```

### 1.3 状态码说明
- **200**：操作成功
- **420**：操作失败
- **0**：查询成功（分页接口）

---

## 2. 用户管理模块

### 2.1 用户登录
**接口地址**：`POST /user/login`

**功能描述**：用户通过用户名、密码和角色进行登录验证，成功后返回Token。

**请求参数**：
```json
{
    "username": "admin",
    "userpassword": "123456",
    "isadmin": 1
}
```

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| userpassword | String | 是 | 密码 |
| isadmin | Byte | 是 | 角色标识（0-读者，1-管理员） |

**响应示例**：
```json
{
    "status": 200,
    "message": "登录成功",
    "data": {
        "token": "a1b2c3d4e5f6..."
    },
    "timestamp": 1711180800
}
```

---

### 2.2 获取用户信息
**接口地址**：`GET /user/info`

**功能描述**：根据Token获取当前登录用户的详细信息。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| token | String | 是 | 登录时返回的Token |

**响应示例**：
```json
{
    "status": 200,
    "message": "获取成功",
    "data": {
        "userid": 1,
        "username": "admin",
        "isadmin": 1
    },
    "timestamp": 1711180800
}
```

---

### 2.3 用户登出
**接口地址**：`GET /user/logout`

**功能描述**：用户退出登录，清除Token缓存。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| token | String | 是 | 登录时返回的Token |

**响应示例**：
```json
{
    "status": 200,
    "message": "登出成功",
    "timestamp": 1711180800
}
```

---

### 2.4 用户注册
**接口地址**：`POST /user/register`

**功能描述**：新用户注册账号，默认创建为普通读者角色。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名（唯一） |
| password | String | 是 | 密码（6-20位） |

**响应示例**：
```json
1  // 返回1表示注册成功，0表示失败
```

---

### 2.5 修改密码
**接口地址**：`POST /user/alterPassword`

**功能描述**：用户修改自己的登录密码。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userid | Integer | 是 | 用户ID |
| username | String | 是 | 用户名 |
| isadmin | Byte | 是 | 角色标识 |
| oldPassword | String | 是 | 旧密码 |
| newPassword | String | 是 | 新密码 |

**响应示例**：
```json
1  // 返回1表示修改成功，0表示失败
```

---

### 2.6 读者修改密码
**接口地址**：`POST /user/reader/alterPassword`

**功能描述**：读者端修改密码接口。

**请求参数**：同2.5

---

### 2.7 获取用户总数（管理员）
**接口地址**：`GET /user/getCount`

**功能描述**：获取系统中用户的总数量。

**权限要求**：管理员

**响应示例**：
```json
50  // 返回用户总数
```

---

### 2.8 查询所有用户（管理员）
**接口地址**：`GET /user/queryUsers`

**功能描述**：查询系统中所有用户信息。

**权限要求**：管理员

**响应示例**：
```json
[
    {
        "userid": 1,
        "username": "admin",
        "userpassword": "******",
        "isadmin": 1
    },
    {
        "userid": 2,
        "username": "reader01",
        "userpassword": "******",
        "isadmin": 0
    }
]
```

---

### 2.9 分页查询用户（管理员）
**接口地址**：`GET /user/queryUsersByPage`

**功能描述**：分页查询用户信息，支持按用户名模糊搜索。

**权限要求**：管理员

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | Integer | 是 | 页码（从1开始） |
| limit | Integer | 是 | 每页数量 |
| username | String | 否 | 用户名（模糊匹配） |

**响应示例**：
```json
{
    "code": 0,
    "message": "success",
    "count": 50,
    "data": [
        {
            "userid": 1,
            "username": "admin",
            "isadmin": 1
        }
    ]
}
```

---

### 2.10 添加用户（管理员）
**接口地址**：`POST /user/addUser`

**功能描述**：管理员添加新用户。

**权限要求**：管理员

**请求参数**：
```json
{
    "username": "newuser",
    "userpassword": "123456",
    "isadmin": 0
}
```

**响应示例**：
```json
1  // 返回1表示添加成功
```

---

### 2.11 删除用户（管理员）
**接口地址**：`DELETE /user/deleteUser`

**功能描述**：管理员删除单个用户。

**权限要求**：管理员

**请求参数**：
```json
{
    "userid": 5
}
```

**响应示例**：
```json
1  // 返回1表示删除成功
```

---

### 2.12 批量删除用户（管理员）
**接口地址**：`DELETE /user/deleteUsers`

**功能描述**：管理员批量删除用户。

**权限要求**：管理员

**请求参数**：
```json
[
    {"userid": 5},
    {"userid": 6},
    {"userid": 7}
]
```

**响应示例**：
```json
3  // 返回删除的数量
```

---

### 2.13 更新用户（管理员）
**接口地址**：`PUT /user/updateUser`

**功能描述**：管理员更新用户信息。

**权限要求**：管理员

**请求参数**：
```json
{
    "userid": 5,
    "username": "updateduser",
    "userpassword": "newpassword",
    "isadmin": 0
}
```

**响应示例**：
```json
1  // 返回1表示更新成功
```

---

## 3. 图书类型管理模块

### 3.1 获取类型总数
**接口地址**：`GET /bookType/getCount`

**功能描述**：获取图书类型的总数量。

**响应示例**：
```json
6  // 返回类型总数
```

---

### 3.2 查询所有类型
**接口地址**：`GET /bookType/queryBookTypes`

**功能描述**：查询所有图书类型信息。

**响应示例**：
```json
[
    {
        "booktypeid": 1,
        "booktypename": "计算机科学",
        "booktypedesc": "计算机相关书籍"
    },
    {
        "booktypeid": 2,
        "booktypename": "文学",
        "booktypedesc": "文学作品"
    }
]
```

---

### 3.3 读者查询所有类型
**接口地址**：`GET /bookType/reader/queryBookTypes`

**功能描述**：读者端查询所有图书类型。

**响应示例**：同3.2

---

### 3.4 分页查询类型（管理员）
**接口地址**：`GET /bookType/queryBookTypesByPage`

**功能描述**：分页查询图书类型，支持按类型名称模糊搜索。

**权限要求**：管理员

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | Integer | 是 | 页码 |
| limit | Integer | 是 | 每页数量 |
| booktypename | String | 否 | 类型名称（模糊匹配） |

**响应示例**：
```json
{
    "code": 0,
    "message": "success",
    "count": 6,
    "data": [
        {
            "booktypeid": 1,
            "booktypename": "计算机科学",
            "booktypedesc": "计算机相关书籍"
        }
    ]
}
```

---

### 3.5 添加类型（管理员）
**接口地址**：`POST /bookType/addBookType`

**功能描述**：管理员添加新的图书类型。

**权限要求**：管理员

**请求参数**：
```json
{
    "booktypename": "科幻",
    "booktypedesc": "科幻小说类"
}
```

**响应示例**：
```json
1  // 返回1表示添加成功
```

---

### 3.6 删除类型（管理员）
**接口地址**：`DELETE /bookType/deleteBookType`

**功能描述**：管理员删除单个图书类型。

**权限要求**：管理员

**请求参数**：
```json
{
    "booktypeid": 5
}
```

**响应示例**：
```json
1  // 返回1表示删除成功
```

---

### 3.7 批量删除类型（管理员）
**接口地址**：`DELETE /bookType/deleteBookTypes`

**功能描述**：管理员批量删除图书类型。

**权限要求**：管理员

**请求参数**：
```json
[
    {"booktypeid": 5},
    {"booktypeid": 6}
]
```

**响应示例**：
```json
2  // 返回删除的数量
```

---

### 3.8 更新类型（管理员）
**接口地址**：`PUT /bookType/updateBookType`

**功能描述**：管理员更新图书类型信息。

**权限要求**：管理员

**请求参数**：
```json
{
    "booktypeid": 5,
    "booktypename": "科幻小说",
    "booktypedesc": "更新后的描述"
}
```

**响应示例**：
```json
1  // 返回1表示更新成功
```

---

## 4. 图书信息管理模块

### 4.1 获取图书总数
**接口地址**：`GET /bookInfo/getCount`

**功能描述**：获取图书的总数量。

**响应示例**：
```json
200  // 返回图书总数
```

---

### 4.2 查询所有图书
**接口地址**：`GET /bookInfo/queryBookInfos`

**功能描述**：查询所有图书信息。

**响应示例**：
```json
[
    {
        "bookid": 1,
        "bookname": "Java编程思想",
        "bookauthor": "Bruce Eckel",
        "bookprice": 108.00,
        "booktypeid": 1,
        "booktypename": "计算机科学",
        "bookdesc": "Java经典书籍",
        "isborrowed": 0,
        "bookimg": "/upload/book1.jpg"
    }
]
```

---

### 4.3 分页查询图书
**接口地址**：`GET /bookInfo/queryBookInfosByPage`

**功能描述**：分页查询图书信息，支持多条件搜索。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | Integer | 是 | 页码 |
| limit | Integer | 是 | 每页数量 |
| bookname | String | 否 | 书名（模糊匹配） |
| bookauthor | String | 否 | 作者（模糊匹配） |
| booktypeid | Integer | 否 | 图书类型ID |

**响应示例**：
```json
{
    "code": 0,
    "message": "success",
    "count": 200,
    "data": [
        {
            "bookid": 1,
            "bookname": "Java编程思想",
            "bookauthor": "Bruce Eckel",
            "bookprice": 108.00,
            "booktypeid": 1,
            "booktypename": "计算机科学",
            "bookdesc": "Java经典书籍",
            "isborrowed": 0,
            "bookimg": "/upload/book1.jpg"
        }
    ]
}
```

---

### 4.4 添加图书（管理员）
**接口地址**：`POST /bookInfo/addBookInfo`

**功能描述**：管理员添加新图书。

**权限要求**：管理员

**请求参数**：
```json
{
    "bookname": "深入理解Java虚拟机",
    "bookauthor": "周志明",
    "bookprice": 89.00,
    "booktypeid": 1,
    "bookdesc": "JVM经典书籍",
    "bookimg": "/upload/jvm.jpg"
}
```

**响应示例**：
```json
1  // 返回1表示添加成功
```

---

### 4.5 删除图书（管理员）
**接口地址**：`DELETE /bookInfo/deleteBookInfo`

**功能描述**：管理员删除单本图书。

**权限要求**：管理员

**请求参数**：
```json
{
    "bookid": 10
}
```

**响应示例**：
```json
1  // 返回1表示删除成功
```

**注意事项**：已借出的图书不允许删除。

---

### 4.6 批量删除图书（管理员）
**接口地址**：`DELETE /bookInfo/deleteBookInfos`

**功能描述**：管理员批量删除图书。

**权限要求**：管理员

**请求参数**：
```json
[
    {"bookid": 10},
    {"bookid": 11},
    {"bookid": 12}
]
```

**响应示例**：
```json
3  // 返回删除的数量
```

---

### 4.7 更新图书（管理员）
**接口地址**：`PUT /bookInfo/updateBookInfo`

**功能描述**：管理员更新图书信息。

**权限要求**：管理员

**请求参数**：
```json
{
    "bookid": 10,
    "bookname": "深入理解Java虚拟机（第3版）",
    "bookauthor": "周志明",
    "bookprice": 99.00,
    "booktypeid": 1,
    "bookdesc": "更新后的描述",
    "bookimg": "/upload/jvm_v3.jpg"
}
```

**响应示例**：
```json
1  // 返回1表示更新成功
```

---

## 5. 借阅管理模块

### 5.1 获取借阅总数
**接口地址**：`GET /borrow/getCount`

**功能描述**：获取借阅记录的总数量。

**响应示例**：
```json
500  // 返回借阅记录总数
```

---

### 5.2 分页查询借阅记录
**接口地址**：`GET /borrow/queryBorrowsByPage`

**功能描述**：分页查询借阅记录，支持按用户和图书筛选。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | Integer | 是 | 页码 |
| limit | Integer | 是 | 每页数量 |
| userid | Integer | 否 | 用户ID |
| bookid | Integer | 否 | 图书ID |

**响应示例**：
```json
{
    "code": 0,
    "message": "success",
    "count": 500,
    "data": [
        {
            "borrowid": 1,
            "userid": 2,
            "username": "reader01",
            "bookid": 5,
            "bookname": "Java编程思想",
            "borrowtime": "2026-03-20 10:30:00",
            "returntime": "2026-03-23 15:20:00"
        }
    ]
}
```

---

### 5.3 借书
**接口地址**：`POST /borrow/borrowBook` 或 `GET /borrow/borrowBook`

**功能描述**：读者借阅图书，系统自动创建借阅记录并更新图书状态。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userid | Integer | 是 | 用户ID |
| bookid | Integer | 是 | 图书ID |

**响应示例**：
```json
1  // 返回1表示借书成功，0表示失败
```

**业务规则**：
- 图书必须处于"可借"状态（isborrowed=0）
- 借书成功后图书状态变为"已借出"（isborrowed=1）
- 自动记录借阅时间
- 使用事务保证数据一致性

---

### 5.4 读者借书
**接口地址**：`POST /borrow/reader/borrowBook` 或 `GET /borrow/reader/borrowBook`

**功能描述**：读者端借书接口。

**请求参数**：同5.3

---

### 5.5 还书
**接口地址**：`POST /borrow/returnBook` 或 `GET /borrow/returnBook`

**功能描述**：读者归还图书，系统更新借阅记录和图书状态。

**请求参数**：
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| borrowid | Integer | 是 | 借阅记录ID |
| bookid | Integer | 是 | 图书ID |

**响应示例**：
```json
1  // 返回1表示还书成功，0表示失败
```

**业务规则**：
- 验证借阅记录是否存在
- 检查是否已经还过书（returntime不为空）
- 还书成功后图书状态变为"可借"（isborrowed=0）
- 自动记录归还时间
- 使用事务保证数据一致性

---

### 5.6 读者还书
**接口地址**：`POST /borrow/reader/returnBook` 或 `GET /borrow/reader/returnBook`

**功能描述**：读者端还书接口。

**请求参数**：同5.5

---

### 5.7 添加借阅记录（管理员）
**接口地址**：`POST /borrow/addBorrow`

**功能描述**：管理员手动添加借阅记录。

**权限要求**：管理员

**请求参数**：
```json
{
    "userid": 2,
    "bookid": 5,
    "borrowtime": "2026-03-20 10:30:00"
}
```

**响应示例**：
```json
1  // 返回1表示添加成功
```

---

### 5.8 删除借阅记录（管理员）
**接口地址**：`DELETE /borrow/deleteBorrow`

**功能描述**：管理员删除单条借阅记录。

**权限要求**：管理员

**请求参数**：
```json
{
    "borrowid": 10
}
```

**响应示例**：
```json
1  // 返回1表示删除成功
```

---

### 5.9 批量删除借阅记录（管理员）
**接口地址**：`DELETE /borrow/deleteBorrows`

**功能描述**：管理员批量删除借阅记录。

**权限要求**：管理员

**请求参数**：
```json
[
    {"borrowid": 10},
    {"borrowid": 11}
]
```

**响应示例**：
```json
2  // 返回删除的数量
```

---

### 5.10 更新借阅记录（管理员）
**接口地址**：`PUT /borrow/updateBorrow`

**功能描述**：管理员更新借阅记录信息。

**权限要求**：管理员

**请求参数**：
```json
{
    "borrowid": 10,
    "userid": 2,
    "bookid": 5,
    "borrowtime": "2026-03-20 10:30:00",
    "returntime": "2026-03-23 15:20:00"
}
```

**响应示例**：
```json
1  // 返回1表示更新成功
```

---

## 6. 文件上传模块

### 6.1 上传图片
**接口地址**：`POST /update/updateImg`

**功能描述**：上传图书封面图片。

**请求参数**：
- Content-Type: `multipart/form-data`
- 参数名：`file`
- 文件类型：jpg、png
- 文件大小：最大10MB

**响应示例**：
```json
{
    "code": 0,
    "data": "/upload/20260323_book_cover.jpg"
}
```

**说明**：
- 文件名会自动添加时间戳防止重复
- 返回的URL可直接用于bookimg字段

---

## 7. 错误码说明

| 错误码 | 说明 | 常见原因 |
|--------|------|----------|
| 420 | 操作失败 | 业务逻辑验证失败 |
| - | 用户名或密码错误 | 登录凭证不正确 |
| - | 角色不匹配 | 登录时选择的角色与实际不符 |
| - | Token无效或已过期 | Token验证失败 |
| - | 图书已借出 | 尝试借阅已被借出的图书 |
| - | 图书不存在 | 操作的图书ID不存在 |
| - | 用户名已存在 | 注册时用户名重复 |
| - | 旧密码错误 | 修改密码时旧密码验证失败 |

---

## 8. 注意事项

### 8.1 认证与授权
- 除登录、注册接口外，所有接口都需要携带有效的Token
- Token通过请求参数传递
- Token有效期为1小时，过期后需重新登录
- 管理员接口需要isadmin=1的用户才能访问

### 8.2 数据验证
- 用户名必须唯一
- 密码长度建议6-20位
- 图书类型ID必须存在于book_type表中
- 已借出的图书不允许删除
- 已归还的借阅记录不允许重复归还

### 8.3 事务处理
- 借书和还书操作使用事务保证数据一致性
- 任何步骤失败都会回滚整个操作
- 并发借阅同一本书时，只有一个请求会成功

### 8.4 性能优化
- 分页查询建议每页不超过50条记录
- 模糊搜索会影响查询性能，建议添加索引
- Token存储在Redis中，提高验证速度

---

## 9. 附录

### 9.1 数据模型关系
```
user (用户表)
  ├─ userid (主键)
  └─ 关联: borrow.userid

book_type (图书类型表)
  ├─ booktypeid (主键)
  └─ 关联: book_info.booktypeid

book_info (图书信息表)
  ├─ bookid (主键)
  ├─ booktypeid (外键 → book_type)
  └─ 关联: borrow.bookid

borrow (借阅记录表)
  ├─ borrowid (主键)
  ├─ userid (外键 → user)
  └─ bookid (外键 → book_info)
```

### 9.2 接口调用示例

#### 完整借书流程
```javascript
// 1. 用户登录
POST /user/login
{
    "username": "reader01",
    "userpassword": "123456",
    "isadmin": 0
}
// 返回: {"status": 200, "data": {"token": "abc123..."}}

// 2. 查询可借图书
GET /bookInfo/queryBookInfosByPage?page=1&limit=10&bookname=Java

// 3. 借书
POST /borrow/reader/borrowBook?userid=2&bookid=5
// 返回: 1 (成功)

// 4. 查询我的借阅记录
GET /borrow/queryBorrowsByPage?page=1&limit=10&userid=2

// 5. 还书
POST /borrow/reader/returnBook?borrowid=1&bookid=5
// 返回: 1 (成功)
```

---

**文档版本**：v1.0
**最后更新**：2026-03-22
**维护团队**：项目开发组
