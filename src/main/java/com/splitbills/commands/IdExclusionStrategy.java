package com.splitbills.commands;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class IdExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        String id = "id";
        return fieldAttributes.getName().toLowerCase().contains(id);
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
