package com.example;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by donald on 2016/3/5.
 */
public class TestParseAttr extends TestCase {
	public void test() {
		String s = "12w-h-4px+7dp";
		Pattern pattern = Pattern.compile("[+-]?(\\d*\\.*\\d*)(\\w+)");
		float value = 0;
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String attr = matcher.group();
			String unit = matcher.group(matcher.groupCount());
			String number = attr.substring(0, attr.length() - unit.length());
			if (number.length() == 1 && (number.equals("-") || number.contains("+"))) {
				number = number + "1";
			} else if (number.length() == 0) number = "1";
			Float u = datas.get(unit);
			if (u != null) {
				value += Float.parseFloat(number) * u;
			} else
				System.out.println("unkown unit " + unit);
		}
		System.out.println("result " + value);
	}

	public void atestStart() {
		getSize("bw:iw+9px");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		datas.put("iw", (float) 100);
		datas.put("ih", (float) 100);
		datas.put("dp", 3.0f);
		datas.put("px", 1.0f);

		datas.put("w", 10f);
		datas.put("h", 30f);
	}

	Map<String, Float> datas = new HashMap<>();

	Pattern pattern = Pattern.compile("(\\d*\\.*\\d*)(\\w+)");

	int getSize(String attr) {//x:12w-5h-4px+7dp
		if (!attr.contains(":")) return 0;

		attr = attr.split(":")[1];//1.5w

		String[] data = attr.split("\\+|-");
		float[] size = new float[2];
		int j = 0;
		for (int i = 0; i < data.length; i++) {//15w,9px
			Matcher matcher = pattern.matcher(data[i]);
			while (matcher.find()) {
				String a = matcher.group(1);//data
				String unit = matcher.group(2);//unit
				float v = a.length() == 0 ? 1 : Float.parseFloat(a);//the data's value
				size[j++] = v * datas.get(unit);
			}
		}
		if (attr.contains("+")) {
			size[0] += size[1];
		} else if (attr.contains("-")) {
			size[0] -= size[1];
		}
		return (int) size[0];
	}
}
