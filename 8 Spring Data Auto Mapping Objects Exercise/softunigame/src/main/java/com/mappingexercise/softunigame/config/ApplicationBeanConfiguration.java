package com.mappingexercise.softunigame.config;

import com.mappingexercise.softunigame.utils.ValidatorUtil;
import com.mappingexercise.softunigame.utils.ValidatorUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.management.MXBean;
import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl(validator());
    }

}
