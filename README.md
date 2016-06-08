# XIntent[ ![Download](https://api.bintray.com/packages/donalddu/maven/com.dhy.xintent/images/download.svg) ](https://bintray.com/donalddu/maven/com.dhy.xintent/_latestVersion)

# 捐赠（感谢所有帮助）![image](http://git.oschina.net/uploads/qrcode/qrcode_alipay_1465351441984120.png)
安卓开发中有一些操作，虽然逻辑很简单，但过程却有点麻烦。这个项目就是提供一些简便的方法，让逻辑和过程同样简单。

# 用Intent传输数据
是安卓中常用技术之一，通常的方法就是声名一堆键名，然后按类别存储到Intent中，然后在其它地方根据键名和类别读取。<br/>
给键取名是很简单的事情，但如果要取太多名的话，就不知道取什么好了，到最后可能干脆用‘KEY_1’什么的。<br/>
但是取个无意义的名，又容易出现用错名或类型错误的问题，怎么破？<br/>
用XIntent传输数据，告别取名的时代！

## 为什么不用EventBus？
因为在一些情况下会出现丢包的问题。经测试3.0.0在6.0模拟器上不能接收到消息。<br/>
接收的页面已经被杀掉了，就不可能收到数据的。页面被杀掉在重启后，也不会再次收到数据。<br/>
比如有一个页面Activity是用来展示订单详情的，在第一次启动后会收到EventBus从上个页面传递过来的数据。<br/>
但如果页面被杀掉，在重启后，就不可能再次收到所需要的数据了。
## Set data
	public static final String KEY_MSG_a = "key_msg_a";
	public static final String KEY_MSG_b = "key_msg_b";
	public static final String KEY_MSG_c = "key_msg_c";
	public static final String KEY_MSG_d = "key_msg_d";

	public void normalSetMethod(String a, boolean b, int c, Data d) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(KEY_MSG_a, a);
		intent.putExtra(KEY_MSG_b, b);
		intent.putExtra(KEY_MSG_c, c);
		intent.putExtra(KEY_MSG_d, d);
		startActivity(intent);
	}
	
	public void XIntentSetMethod(String a, boolean b, int c, Data d) {
		startActivity(new XIntent(this, MainActivity.class, a, b, c, d));
	}
## Get data
	void normalGetMethod() {
		Intent intent = getIntent();
		String a = intent.getStringExtra(KEY_MSG_a);
		boolean b = intent.getBooleanExtra(KEY_MSG_b, false);
		int c = intent.getIntExtra(KEY_MSG_c, 1);
		Date d = (Date) intent.getSerializableExtra(KEY_MSG_d);
	}

	void XIntentGetMethod() {
		String a = XIntent.readSerializableExtra(this, String.class, "");
		boolean b = XIntent.readSerializableExtra(this, Boolean.class, false);
		int c = XIntent.readSerializableExtra(this, Integer.class, 1);
		Data d = XIntent.readSerializableExtra(this, Data.class);
	}
## Support handle intent extra directly
	public void XIntentSetResult(String a, boolean b, int c, Data d) {
		Intent intent = new Intent();
		XIntent.putSerializableExtra(intent, a, b, c, d);
		setResult(RESULT_OK, intent);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		String a = XIntent.readSerializableExtra(data, String.class, "");
		boolean b = XIntent.readSerializableExtra(data, Boolean.class, false);
		int c = XIntent.readSerializableExtra(data, Integer.class, 1);
		Data d = XIntent.readSerializableExtra(data, Data.class);
	}
## Gradle dependency
	compile 'com.dhy:xintent:1.0.11'
