package com.example.ot.otusers.util;

import com.example.ot.otusers.model.RdfFormat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class RdfFormatConverter implements Converter<String, RdfFormat> {

    @Override
    public RdfFormat convert(@Nullable String source) {
        return RdfFormat.getByName(source);
    }

}
