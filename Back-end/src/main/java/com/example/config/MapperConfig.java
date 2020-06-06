package com.example.config;

import com.example.converter.StringLocalDateConverter;
import com.example.converter.StringPriorityConverter;
import com.example.converter.StringStatusConverter;
import com.example.converter.StringTrustLevelConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MapperConfig {
    private final StringTrustLevelConverter stringTrustLevelConverter;
    private final StringStatusConverter stringStatusConverter;
    private final StringPriorityConverter stringPriorityConverter;
    private final StringLocalDateConverter stringLocalDateConverter;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(stringTrustLevelConverter);
        modelMapper.addConverter(stringStatusConverter);
        modelMapper.addConverter(stringPriorityConverter);
        modelMapper.addConverter(stringLocalDateConverter);

        return modelMapper;
    }
}
