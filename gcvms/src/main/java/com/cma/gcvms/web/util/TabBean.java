package com.cma.gcvms.web.util;

import java.io.Serializable;

/**
 * Tracks the <code>p:tabView</code> active index.
 */
public class TabBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int activeIndex = 0;

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getActiveIndex() {
        return activeIndex;
    }
}
