package com.example.converter;

import com.example.model.TrustLevel;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class StringTrustLevelConverter implements Converter<String, TrustLevel> {

    @Override
    public TrustLevel convert(MappingContext<String, TrustLevel> context) {
        if (context.getSource() != null) {
            return TrustLevel.getFromName(context.getSource());
        }
        return null;
    }

}
