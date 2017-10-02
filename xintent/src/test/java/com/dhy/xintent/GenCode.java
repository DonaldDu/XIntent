package com.dhy.xintent;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.view.View;

import com.dhy.xintent.interfaces.IFindViewById;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class GenCode {
    private Class[] classes = {Activity.class, Dialog.class, View.class, IFindViewById.class, Fragment.class, android.support.v4.app.Fragment.class};

    @Test
    public void testGenCodeOfSetImage() {
        String base64 = "cHVibGljIHN0YXRpYyBJbWFnZVZpZXcgc2V0SW1hZ2UoJTEkcyAlMiRzLCBASWRSZXMgaW50IHJpZCwgQERyYXdhYmxlUmVzIGludCBpbWFnZSwgQE51bGxhYmxlIGZpbmFsIEJvb2xlYW4gc2hvdykgewogICAgICAgIHJldHVybiBzZXRJbWFnZSgoSW1hZ2VWaWV3KSAlMyRzLmZpbmRWaWV3QnlJZChyaWQpLCBpbWFnZSwgc2hvdyk7CiAgICB9CgogICAgcHVibGljIHN0YXRpYyBJbWFnZVZpZXcgc2V0SW1hZ2UoJTEkcyAlMiRzLCBASWRSZXMgaW50IHJpZCwgQERyYXdhYmxlUmVzIGludCBpbWFnZSwgQFZpc2liaWxpdHkgQE51bGxhYmxlIGZpbmFsIEludGVnZXIgdmlzaWJpbGl0eSkgewogICAgICAgIHJldHVybiBzZXRJbWFnZSgoSW1hZ2VWaWV3KSAlMyRzLmZpbmRWaWV3QnlJZChyaWQpLCBpbWFnZSwgdmlzaWJpbGl0eSk7CiAgICB9CgogICAgcHVibGljIHN0YXRpYyBJbWFnZVZpZXcgc2V0SW1hZ2UoJTEkcyAlMiRzLCBASWRSZXMgaW50IHJpZCwgQERyYXdhYmxlUmVzIGludCBpbWFnZSkgewogICAgICAgIHJldHVybiBzZXRJbWFnZSgoSW1hZ2VWaWV3KSAlMyRzLmZpbmRWaWV3QnlJZChyaWQpLCBpbWFnZSk7CiAgICB9CgogICAgcHVibGljIHN0YXRpYyBJbWFnZVZpZXcgc2V0SW1hZ2UoJTEkcyAlMiRzLCBASWRSZXMgaW50IHJpZCwgVXJpIHVyaSwgQE51bGxhYmxlIGZpbmFsIEJvb2xlYW4gc2hvdykgewogICAgICAgIHJldHVybiBzZXRJbWFnZSgoSW1hZ2VWaWV3KSAlMyRzLmZpbmRWaWV3QnlJZChyaWQpLCB1cmksIHNob3cpOwogICAgfQoKICAgIHB1YmxpYyBzdGF0aWMgSW1hZ2VWaWV3IHNldEltYWdlKCUxJHMgJTIkcywgQElkUmVzIGludCByaWQsIFVyaSB1cmksIEBWaXNpYmlsaXR5IEBOdWxsYWJsZSBmaW5hbCBJbnRlZ2VyIHZpc2liaWxpdHkpIHsKICAgICAgICByZXR1cm4gc2V0SW1hZ2UoKEltYWdlVmlldykgJTMkcy5maW5kVmlld0J5SWQocmlkKSwgdXJpLCB2aXNpYmlsaXR5KTsKICAgIH0KCiAgICBwdWJsaWMgc3RhdGljIEltYWdlVmlldyBzZXRJbWFnZSglMSRzICUyJHMsIEBJZFJlcyBpbnQgcmlkLCBVcmkgdXJpKSB7CiAgICAgICAgcmV0dXJuIHNldEltYWdlKChJbWFnZVZpZXcpICUzJHMuZmluZFZpZXdCeUlkKHJpZCksIHVyaSk7CiAgICB9";
        printTypeParamFinder(base64);
    }

    @Test
    public void testGenCodeOfSetText() {
        String base64 = "cHVibGljIHN0YXRpYyBUZXh0VmlldyBzZXRUZXh0KCUxJHMgJTIkcywgQElkUmVzIGludCByaWQsIE9iamVjdCB2YWx1ZSwgQE51bGxhYmxlIEJvb2xlYW4gc2hvdykgewogICAgICAgIHJldHVybiBzZXRUZXh0KChUZXh0VmlldykgJTMkcy5maW5kVmlld0J5SWQocmlkKSwgdmFsdWUsIHNob3cpOwogICAgfQoKICAgIHB1YmxpYyBzdGF0aWMgVGV4dFZpZXcgc2V0VGV4dCglMSRzICUyJHMsIEBJZFJlcyBpbnQgcmlkLCBPYmplY3QgdmFsdWUsIEBWaXNpYmlsaXR5IEBOdWxsYWJsZSBmaW5hbCBJbnRlZ2VyIHZpc2liaWxpdHkpIHsKICAgICAgICByZXR1cm4gc2V0VGV4dCgoVGV4dFZpZXcpICUzJHMuZmluZFZpZXdCeUlkKHJpZCksIHZhbHVlLCB2aXNpYmlsaXR5KTsKICAgIH0KCiAgICBwdWJsaWMgc3RhdGljIFRleHRWaWV3IHNldFRleHQoJTEkcyAlMiRzLCBASWRSZXMgaW50IHJpZCwgT2JqZWN0IHZhbHVlKSB7CiAgICAgICAgcmV0dXJuIHNldFRleHQoKFRleHRWaWV3KSAlMyRzLmZpbmRWaWV3QnlJZChyaWQpLCB2YWx1ZSk7CiAgICB9";
        printTypeParamFinder(base64);
    }

    @Test
    public void testGenCodeOfSetTextWithFormat() {
        String base64 = "cHVibGljIHN0YXRpYyBUZXh0VmlldyBzZXRUZXh0V2l0aEZvcm1hdCglMSRzICUyJHMsIEBJZFJlcyBpbnQgcmlkLCBPYmplY3QgdmFsdWUsIGZpbmFsIGJvb2xlYW4gc2hvdykgewogICAgICAgIHJldHVybiBzZXRUZXh0V2l0aEZvcm1hdCgoVGV4dFZpZXcpICUzJHMuZmluZFZpZXdCeUlkKHJpZCksIHZhbHVlLCBzaG93KTsKICAgIH0KCiAgICBwdWJsaWMgc3RhdGljIFRleHRWaWV3IHNldFRleHRXaXRoRm9ybWF0KCUxJHMgJTIkcywgQElkUmVzIGludCByaWQsIE9iamVjdCB2YWx1ZSwgQFZpc2liaWxpdHkgQE51bGxhYmxlIGZpbmFsIEludGVnZXIgdmlzaWJpbGl0eSkgewogICAgICAgIHJldHVybiBzZXRUZXh0V2l0aEZvcm1hdCgoVGV4dFZpZXcpICUzJHMuZmluZFZpZXdCeUlkKHJpZCksIHZhbHVlLCB2aXNpYmlsaXR5KTsKICAgIH0KCiAgICBwdWJsaWMgc3RhdGljIFRleHRWaWV3IHNldFRleHRXaXRoRm9ybWF0KCUxJHMgJTIkcywgQElkUmVzIGludCByaWQsIE9iamVjdCB2YWx1ZSkgewogICAgICAgIHJldHVybiBzZXRUZXh0V2l0aEZvcm1hdCgoVGV4dFZpZXcpICUzJHMuZmluZFZpZXdCeUlkKHJpZCksIHZhbHVlKTsKICAgIH0=";
        printTypeParamFinder(base64);
    }

    private void printTypeParamFinder(String base64EncodeFormat) {
        String format = new String(Base64.decodeBase64(base64EncodeFormat));
        System.out.println("//region");
        for (Class cls : classes) {
            printTypeParamFinder(format, cls);
        }
        System.out.println("//endregion");
    }

    private void printTypeParamFinder(String format, Class cls) {
        String type = getTypeName(cls);
        String param = getParamName(cls);
        String finder = getFinder(cls, param);
        System.out.println(String.format(format, type, param, finder));
    }

    private String getTypeName(Class cls) {
        return cls == android.support.v4.app.Fragment.class ? cls.getName() : cls.getSimpleName();
    }

    private String getFinder(Class cls, String param) {
        return cls.getSimpleName().equals("Fragment") ? param + ".getView()" : param;
    }

    private String getParamName(Class cls) {
        String name = cls.getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }
}
