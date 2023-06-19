package org.example;

@FunctionalInterface
public interface TetraFunction<T, X, U, V, R> {
    R apply(T t, X x, U u, V v);
}
