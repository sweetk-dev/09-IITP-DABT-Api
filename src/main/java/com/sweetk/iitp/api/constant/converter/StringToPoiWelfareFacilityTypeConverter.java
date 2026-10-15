package com.sweetk.iitp.api.constant.converter;

import com.sweetk.iitp.api.constant.poi.PoiWelfareFacilityType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPoiWelfareFacilityTypeConverter implements Converter<String, PoiWelfareFacilityType> {
    @Override
    public PoiWelfareFacilityType convert(String source) {
        return PoiWelfareFacilityType.fromString(source);
    }
}
