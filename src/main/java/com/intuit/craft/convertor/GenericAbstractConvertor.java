package com.intuit.craft.convertor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic abstract convertor implementing only {@link Convertor#convert(List)}}.
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 */
public abstract class GenericAbstractConvertor<T, U> implements Convertor<T, U> {
    @Override
    public List<U> convert(List<T> t) {
        return t.stream().map(this::convert).collect(Collectors.toList());
    }
}
