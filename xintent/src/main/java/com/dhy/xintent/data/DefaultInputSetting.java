package com.dhy.xintent.data;

import androidx.annotation.NonNull;
import android.view.inputmethod.EditorInfo;

public class DefaultInputSetting {
    public final String text, hint;

    public final String buttonCommit;

    public final String buttonCancel;

    public final boolean canEmpty;
    public final Integer inputType;

    private DefaultInputSetting(Builder builder) {
        this.text = builder.text;
        this.hint = builder.hint;
        this.inputType = builder.inputType;
        this.buttonCommit = builder.buttonCommit;
        this.buttonCancel = builder.buttonCancel;
        this.canEmpty = builder.canEmpty;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private String text, hint;
        private String buttonCommit = "确定";
        private String buttonCancel = "取消";
        private boolean canEmpty = false;
        private Integer inputType;

        private Builder() {
        }

        /**
         * @param text 默认要输入的内容，可为空
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        /**
         * {@link EditorInfo#inputType}
         */
        public Builder inputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        /**
         * default: "确定"
         */
        public Builder buttonCommit(@NonNull String buttonCommit) {
            this.buttonCommit = buttonCommit;
            return this;
        }

        /**
         * default: "取消"
         */
        public Builder buttonCancel(@NonNull String buttonCancel) {
            this.buttonCancel = buttonCancel;
            return this;
        }

        /**
         * default: "false"
         */
        public Builder canEmpty(boolean canEmpty) {
            this.canEmpty = canEmpty;
            return this;
        }

        public DefaultInputSetting build() {
            return new DefaultInputSetting(this);
        }
    }
}
