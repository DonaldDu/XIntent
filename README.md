# XIntent
An easy way to handle intent extra.<p>

	public static void putSerializableExtra(Intent intent, Serializable... serializable) {
		intent.putExtra(KEY_EXTRA, serializable.length > 1 ? serializable : serializable[0]);
	}
#set data
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
#get data
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
