package dev.drew.restaurantreview.util.interfaces;

public interface EntityUserIdProvider<T> {
    Long getUserId(T entity);
}
