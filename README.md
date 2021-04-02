# XIntent [![](https://jitpack.io/v/DonaldDu/XIntent.svg)](https://jitpack.io/#DonaldDu/XIntent)

用Intent传输数据是安卓常用技术之一，通常的方法是声名一堆键名(key_name)，把数据存储到Intent中，然后在其它地方根据键名和类别读取。

示例如下：
```
val KEY_USER_NAME = "key_user_name"
val KEY_USER_PWD = "key_user_pwd"
val KEY_USER_NEW = "key_user_new"

val intent = Intent()
intent.putExtra(KEY_USER_NAME, "name")
intent.putExtra(KEY_USER_PWD, 123)
intent.putExtra(KEY_USER_NEW, true)

val name = intent.getStringExtra(KEY_USER_NAME)
val pwd = intent.getIntExtra(KEY_USER_PWD, -1)
val newUser = intent.getBooleanExtra(KEY_USER_NEW, false)
```

虽然取键名很简单，但如果取太多的话，就不知道取什么好了，到最后可能干脆直接用‘KEY_1’什么的。取无意义键名，容易出现用错名或类型错误的问题，怎么破？用XIntent传输数据，超简洁！

更多使用请看 [详细介绍](https://www.jianshu.com/p/5989577231e3)
