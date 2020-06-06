package com.example.converter;

import com.example.model.Status;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class StringStatusConverter implements Converter<String, Status> {

    @Override
    public Status convert(MappingContext<String, Status> context) {
        if (context.getSource() != null) {
            return Status.getFromName(context.getSource());
        }
        return null;
    }

}
