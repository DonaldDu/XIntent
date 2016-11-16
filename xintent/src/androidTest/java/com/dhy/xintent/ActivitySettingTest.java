package com.dhy.xintent;

import com.dhy.xintent.util.BaseActivityUnitTestCase;

public class ActivitySettingTest extends BaseActivityUnitTestCase {
	public void testDebugStatus() {
		assertNotNull(XCommon.isDebug(getActivity()));
		assertNotNull(XCommon.isDebug(getActivity()));
	}

	public void testSetting_baseDataType() {
		Object[] values = {"testSetting_baseDataType", 1, true, 7f, 4d};
		for (Object value : values) {
			XCommon.updateSetting(getActivity(), value);
			Object setting = XCommon.getSetting(getActivity(), value.getClass());
			assertEquals(value, setting);
		}
		XCommon.clearSettings(getActivity());
	}

	public void testSetting_classData() {
		Data data = new Data();
		data.a = 1;
		data.b = 2;
		XCommon.updateSetting(getActivity(), data);
		Data setting = XCommon.getSetting(getActivity(), Data.class);
		assertEquals(setting.sum(), data.sum());
		XCommon.clearSettings(getActivity());
	}

	public void testSetting_exist_remove() {
		String value, setting;
		value = getClass().getName();
		XCommon.updateSetting(getActivity(), value);
		setting = XCommon.getSetting(getActivity());
		assertEquals(value, setting);

		XCommon.updateSetting(getActivity(), null);
		setting = XCommon.getSetting(getActivity());
		assertNull(setting);
		XCommon.clearSettings(getActivity());
	}

	public void testSettings_remove() {
		String value, setting;
		value = getClass().getName();
		XCommon.updateSetting(getActivity(), value);
		setting = XCommon.getSetting(getActivity());
		assertEquals(value, setting);

		XCommon.clearSettings(getActivity());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
		setting = XCommon.getSetting(getActivity());
		assertNull(setting);
	}

	class Data {
		int a, b;

		int sum() {
			return a + b;
		}
	}
}
