package com.sweetk.iitp.api.constant.converter;

import com.sweetk.iitp.api.constant.poi.PoiEmergencySupportType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPoiEmergencySupportTypeConverter implements Converter<String, PoiEmergencySupportType> {
    @Override
    public PoiEmergencySupportType convert(String source) {
        return PoiEmergencySupportType.fromString(source);
    }
}
