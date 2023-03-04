package com.jvmfrog.components.bottomnavigationview.api;

import androidx.annotation.DrawableRes;

public abstract class IconSource {
    public static class Url extends IconSource {
        private final String url;

        public Url(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class ResourceId extends IconSource {
        private final int drawableResourceId;

        public ResourceId(int drawableResourceId) {
            this.drawableResourceId = drawableResourceId;
        }

        @DrawableRes
        public int getDrawableResourceId() {
            return drawableResourceId;
        }
    }

    public static class NotDefined extends IconSource {
    }

    public static Url url(String url) {
        return new Url(url);
    }

    public static ResourceId resource(@DrawableRes int resourceId) {
        return new ResourceId(resourceId);
    }

    public static NotDefined notDefined() {
        return new NotDefined();
    }
}