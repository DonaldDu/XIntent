package com.dhy.xintent.preferences;

class SimpleFileNameGenerator implements IFileNameGenerator {
    @Override
    public String generate(Class<? extends Enum> cls) {
        return cls.getName();
    }
}
