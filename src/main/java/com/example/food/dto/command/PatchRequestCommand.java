package com.example.food.dto.command;

import java.io.Serializable;
import java.util.HashMap;

public class PatchRequestCommand implements Serializable {
    protected HashMap patchRequestData = new HashMap();

    public boolean contains(String key) {
        return patchRequestData.containsKey(key);
    }

    protected <T> T as(Class<T> t, Object o) {
        return t.isInstance(o) ? t.cast(o) : null;
    }
}
