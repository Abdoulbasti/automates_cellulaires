package org.example;



/**
 * Représente une fonction qui accepte trois arguments et produit un résultat.
 * C'est une extension fonctionnelle spéciale pour des opérations qui nécessitent trois arguments d'entrée.
 *
 * @param <T> le type du premier argument de la fonction
 * @param <U> le type du deuxième argument de la fonction
 * @param <V> le type du troisième argument de la fonction
 * @param <R> le type du résultat de la fonction
 *
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}