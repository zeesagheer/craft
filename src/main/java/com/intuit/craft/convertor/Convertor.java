package com.intuit.craft.convertor;

import java.util.List;

public interface Convertor<T, U> {
    U convert(T t);

    List<U> convert(List<T> t);
}
