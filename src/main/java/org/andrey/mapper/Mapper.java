package org.andrey.mapper;

import org.springframework.stereotype.Component;

public interface Mapper <F, T> {

    T map(F fromObject);

    default T map(F fromObject, T toObject) {
        return toObject;
    }

}
