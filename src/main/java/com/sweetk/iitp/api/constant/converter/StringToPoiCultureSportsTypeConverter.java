package com.sweetk.iitp.api.constant.converter;

import com.sweetk.iitp.api.constant.poi.PoiCultureSportsType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPoiCultureSportsTypeConverter implements Converter<String, PoiCultureSportsType> {
    @Override
    public PoiCultureSportsType convert(String source) {
        return PoiCultureSportsType.fromString(source);
    }
}
