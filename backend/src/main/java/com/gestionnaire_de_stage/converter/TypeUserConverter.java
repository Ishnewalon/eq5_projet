package com.gestionnaire_de_stage.converter;

import com.gestionnaire_de_stage.enums.TypeUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TypeUserConverter implements Converter<String, TypeUser> {

    @Override
    public TypeUser convert(String value) {
        return TypeUser.valueOf(value.toUpperCase());
    }

}
