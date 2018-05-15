# XIntent [![](https://jitpack.io/v/DonaldDu/XIntent.svg)](https://jitpack.io/#DonaldDu/XIntent)
安卓开发中有一些操作，虽然逻辑很简单，但过程却有点麻烦。这个项目就是提供一些方法，让逻辑和过程同样简单。

## 用Intent传输数据
是安卓常用技术之一，通常的方法是声名一堆键名(key_name)，把数据存储到Intent中，然后在其它地方根据键名和类别读取。取键名是很简单的事情，但如果取太多的话，就不知道取什么好了，到最后可能干脆直接用‘KEY_1’什么的。取无意义键名，容易出现用错名或类型错误的问题，怎么破？用XIntent传输数据，告别取名的时代！

## 为什么不用EventBus？
1. 在一些情况下会出现丢包的问题。
2. 经测试3.0.0在6.0模拟器上不能接收到消息。
3. 接收的页面已经被杀掉了，就不能收到消息。
4. 页面被杀掉重启后，也不能再次收到消息。比如有一个展示订单详情的页面，在第一次启动后会收到EventBus的消息。但如果页面被杀掉，在重启后，就不能再次收到的消息了。

## 写入数据
	void setExtra(String a, boolean b, int c, Data d) {
		startActivity(new XIntent(context, MainActivity.class, a, b, c, d));
		//or
		XIntent.startActivity(context, MainActivity.class, a, b, c, d);
	}
## 读出数据
	void getExtra() {
		String a = XIntent.readSerializableExtra(activity, String.class, "");
		boolean b = XIntent.readSerializableExtra(activity, Boolean.class, false);
		int c = XIntent.readSerializableExtra(activity, Integer.class, 1);
		Data d = XIntent.readSerializableExtra(activity, Data.class);
	}
## Intent
	public void XIntentSetResult(String a, boolean b, int c, Data d) {
		Intent intent = new Intent();
		XIntent.putSerializableExtra(intent, a, b, c, d);
		setResult(RESULT_OK, intent);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		String a = XIntent.readSerializableExtra(intent, String.class, "");
		boolean b = XIntent.readSerializableExtra(intent, Boolean.class, false);
		int c = XIntent.readSerializableExtra(intent, Integer.class, 1);
		Data d = XIntent.readSerializableExtra(intent, Data.class);
	}
## Bundle
    protected void onSaveInstanceState(Bundle outState) {
        XIntent.putSerializableExtra(outState, "s", 1);
        super.onSaveInstanceState(outState);
    }
 
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        
        Intent intent = new XIntent(savedInstanceState);
        String a = XIntent.readSerializableExtra(intent, String.class, "");
        Integer b = XIntent.readSerializableExtra(intent, Integer.class, 0);
    }
## Gradle dependency

![Release](https://jitpack.io/v/DonaldDu/XIntent.svg)
(https://jitpack.io/#DonaldDu/XIntent)

    allprojects {
        repositories {
            mavenLocal()
            maven { url 'https://jitpack.io' }//add this to project build.gradle
        }
    }

    compile 'com.github.DonaldDu:XIntent:xxx'//add this to module's build.gradle


# XCommon —— The most Common util

# 捐赠（感谢所有帮助）![image](https://raw.githubusercontent.com/DonaldDu/XIntent/master/qrcode_alipay.png)
