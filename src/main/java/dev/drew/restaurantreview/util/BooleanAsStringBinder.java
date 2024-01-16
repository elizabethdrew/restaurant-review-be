package dev.drew.restaurantreview.util;

import org.hibernate.search.mapper.pojo.bridge.binding.ValueBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.ValueBinder;

public class BooleanAsStringBinder implements ValueBinder {

    @Override
    public void bind(ValueBindingContext<?> context) {
        context.bridge( Boolean.class, new BooleanAsStringBridge(
                "true", // True value as string
                "false" // False value as string
        ) );
    }
}