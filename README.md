# InMemoryTelecomService
Oracle TimesTen 内存数据库项目

## Prototype WebPages

[原型页面传送门](http://net.neverstar.top)

**User** for testing<br>
account : 10086<br>
password: 10086

**Admin** for testing<br>
account : admin<br>
password: admin

## 前后端交互API

请后端设置返回JSON类型<br>
response.setContentType("application/json");

以下 {} 表示数据, 括号内表示数据类型或真正数值

example ：<br>
{bool} 表示bool值<br>

>这里我们做简单一点 <br>
>我前端保存登录状态<br>
>后端就不用管cookie和session


### 1.登录

```
UrlPattern = "/signin"

[POST]
@request
{
	phone_number : {String},
	password : {String}
}

@response
{
	"result" : {bool}
}
```

### 2.查询余额

```
UrlPattern = "/balance"]

[POST]
@request
{
	phone_number : {String}
}

@response
{
	"balance" : {double},			/*余额*/
	"plan" : {String}				/*套餐名称*/
}
```

### 3.充值

```
UrlPattern = "/recharge"

[POST]
@request
{
	phone_number : {String},
	amount : {Integer},				/*充值金额*/
}

@response
{
	"result" : {bool}
}
```

### 4.查询充值记录

>查询记录，总是返回一个length小于等于10的数组<br>
>查询结果按时间排序，最新的在最前面<br>
>begin表示从第几个开始(minValue = 0)，然后返回接着的10条记录(少于10条则返回真实数量条目)

```
UrlPattern = "/rechargerecord"

[POST]
@request
{
	phone_number : {String},
	begin : {Integer}
}

@response
{[
	{
		"time" : {"yyyy/mm/dd hh:mm:ss"},	/*充值时间*/
		"amount" : {Integer}				/*充值金额*/
	},
	{
		"time" : {"yyyy/mm/dd hh:mm:ss"},
		"amount" : {Integer}
	},
	...
]}
```

### 5.查询流水

```
UrlPattern = "/record"

[POST]
@request
{
	phone_number : {String},
	begin : {Integer}
}

@response
{[
	{
		"caller" : {String},					/*主叫*/
		"called" : {String},					/*被叫*/
		"call_time" : {"yyyy/mm/dd hh:mm:ss"},	/*呼叫时间*/
		"dur_time" : {Integer},					/*通话时长 毫秒数*/
		"cost" : {double}						/*消费金额*/
	},
	{
		"caller" : {String},
		"called" : {String},
		"call_time" : {"yyyy/mm/dd hh:mm:ss"},
		"dur_time" : {Integer},	
		"cost" : {double}
	},
	...
]}
```


### 6.查询通话量

```
UrlPattern = "/calltotaltime"

[POST]
@request
{
	from : {"yyyy-mm-dd"},
	to : {"yyyy-mm-dd"}
}

@response
{[
	{
		"totalTime" : {Integer},						/*通话总时长*/
		"time" : {"yyyy-mm-dd"},						/*查询时间*/
	},
	{
		"totalTime" : {Integer},
		"time" : {"yyyy-mm-dd"},
	},
	...
]}
```

### 7.查询通话总额

```
UrlPattern = "/calltotalamount"

[POST]
@request
{
	from : {"yyyy-mm-dd"},
	to : {"yyyy-mm-dd"}
}

@response
{[
	{
		"TotalTime" : {Integer},						/*通话总额 单位：千万元*/ PS:这个数量级我不太确定
		"Time" : {"yyyy-mm-dd"},						/*查询时间*/
	},
	{
		"TotalTime" : {Integer},
		"Time" : {"yyyy-mm-dd"},
	},
	...
]}
```

### 8.查询通话时长分布

```
UrlPattern = "/callduration"

[POST]
@request
{
	from : {Integer},								/*通话时长最小值 单位：分钟*/
	to : {Integer}									/*通话时长最大值 单位：分钟*/
}

@response
{[
	{
		"duration" : {Integer},						/*通话时长 单位：分钟*/
		"quantity" : {Integer},						/*通话数量 单位：千万通*/ PS:这个数量级我不太确定
	},
	{
		"duration" : {Integer},
		"quantity" : {Integer},
	},
	...
]}
```

### 9.查询新增用户量

```
UrlPattern = "/newuser"

[POST]
@request
{
	from : {"yyyy-mm"},
	to : {"yyyy-mm"}
}

@response
{[
	{
		"time" : {"yyyy-mm"},						/*查询时间*/
		"quantity" : {Integer},						/*通话数量 单位：万个*/ PS:这个数量级我不太确定
	},
	{
		"time" : {"yyyy-mm"},
		"quantity" : {Integer},	
	},
	...
]}
```