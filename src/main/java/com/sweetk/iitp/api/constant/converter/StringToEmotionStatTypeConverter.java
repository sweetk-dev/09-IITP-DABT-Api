package com.sweetk.iitp.api.constant.converter;

import com.sweetk.iitp.api.constant.basic.EmotionStatType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEmotionStatTypeConverter implements Converter<String, EmotionStatType> {
    @Override
    public EmotionStatType convert(String source) {
        return EmotionStatType.fromString(source);
    }
}
