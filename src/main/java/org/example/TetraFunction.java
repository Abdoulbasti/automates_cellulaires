package org.example;


/**
 * Représente une fonction qui accepte quatre arguments et produit un résultat.
 * Il s'agit d'une extension fonctionnelle pour des opérations qui nécessitent quatre arguments d'entrée.
 *
 * @param <T> le type du premier argument de la fonction
 * @param <X> le type du deuxième argument de la fonction
 * @param <U> le type du troisième argument de la fonction
 * @param <V> le type du quatrième argument de la fonction
 * @param <R> le type du résultat de la fonction
 *
 */
@FunctionalInterface
public interface TetraFunction<T, X, U, V, R> {
    R apply(T t, X x, U u, V v);
}