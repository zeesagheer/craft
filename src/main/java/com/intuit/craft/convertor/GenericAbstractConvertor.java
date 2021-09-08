package com.intuit.craft.convertor;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericAbstractConvertor<T, U> implements Convertor<T, U> {
    @Override
    public List<U> convert(List<T> t) {
        return t.stream().map(this::convert).collect(Collectors.toList());
    }
}
