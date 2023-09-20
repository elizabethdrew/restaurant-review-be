package dev.drew.restaurantreview.util.interfaces;

public interface EntityOwnerIdProvider<T> {
    Long getOwnerId(T entity);
}
