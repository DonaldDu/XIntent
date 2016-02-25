# XIntent
	public static final String KEY_MSG_a = "key_msg";
	public static final String KEY_MSG_b = "key_msg";
	public static final String KEY_MSG_c = "key_msg";
	public static final String KEY_MSG_d = "key_msg";

	public void normalSetMethod(Data data) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(KEY_MSG_a, "");
		intent.putExtra(KEY_MSG_b, false);
		intent.putExtra(KEY_MSG_c, 1);
		intent.putExtra(KEY_MSG_d, data);
		startActivity(intent);
	}

	public void XIntentSetMethod(Data data) {
		startActivity(new XIntent(this, MainActivity.class, "", false, 1, data));
	}
	
	void normalGetMethod() {
		Intent intent = getIntent();
		String msg = intent.getStringExtra(KEY_MSG_a);
		boolean b = intent.getBooleanExtra(KEY_MSG_b, false);
		int a = intent.getIntExtra(KEY_MSG_c, 1);
		Date data = (Date) intent.getSerializableExtra(KEY_MSG_d);
	}

	void XIntentGetMethod() {
		XIntent.readSerializableExtra(this,String.class);
		String msg = XIntent.readSerializableExtra(this, String.class, "");
		boolean b = XIntent.readSerializableExtra(this, Boolean.class, false);
		int a = XIntent.readSerializableExtra(this, Integer.class, 1);
		Data data = XIntent.readSerializableExtra(this, Data.class);
	}