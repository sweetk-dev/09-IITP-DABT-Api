package com.sweetk.iitp.api.constant.converter;

import com.sweetk.iitp.api.constant.MvPoiCategoryType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMvPoiCategoryTypeConverter implements Converter<String, MvPoiCategoryType> {
    @Override
    public MvPoiCategoryType convert(String source) {
        return MvPoiCategoryType.fromString(source);
    }
} 