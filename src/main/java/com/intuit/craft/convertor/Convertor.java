package com.intuit.craft.convertor;

import java.util.List;

/**
 * Generic Convertor to convert between two types.
 *
 * @param <T> source object type
 * @param <U> destination object type
 */
public interface Convertor<T, U> {
    /**
     * Convert u.
     *
     * @param t source object
     * @return converted object
     */
    U convert(T t);

    /**
     * Convert list.
     *
     * @param t @see {@link List} of source objects
     * @return the {@link List} of converted objects
     */
    List<U> convert(List<T> t);
}
