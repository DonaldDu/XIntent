package com.dhy.xintentsample;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by donald on 2016/3/5.
 */
public class TestParseAttr extends TestCase {
	public void testStart() {
		datas.put("iw", (float) 100);
		datas.put("ih", (float) 100);
		datas.put("dp", 3.0f);
		datas.put("px", 1.0f);
		getSize("bw:12w-px");
	}

	Map<String, Float> datas = new HashMap<>();

	int getSize(String attr) {//1.5w,15w-9dp,15w+9px
		if (!attr.contains(":")) return 0;
		attr = attr.split(":")[1];//1.5w
		String[] data = attr.split("\\+|-");
		Pattern pattern = Pattern.compile("(\\d*)(\\w+)");
		for (String d : data) {
			System.out.println("matcher " + d);
			Matcher matcher = pattern.matcher(d);
			while (matcher.find()) {
				System.out.println(matcher.group());
			}
		}
		return 0;
	}
}
