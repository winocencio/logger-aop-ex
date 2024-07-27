package com.winocencio.example.service;

import com.winocencio.config.logger.annotation.LoggerMethodInvocation;
import com.winocencio.example.controller.dto.ExampleDto;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {


    @LoggerMethodInvocation
    public ExampleDto transform(ExampleDto exampleDto) throws Exception {

        if(exampleDto.getFieldOne() == null)
            throw new Exception("FieldOne is null");

        if(exampleDto.getFieldTwo() == null)
            throw new Exception("FieldTwo is null");

        exampleDto
            .setFieldOne(exampleDto.getFieldOne() + " TRANSFORMED")
            .setFieldTwo(exampleDto.getFieldTwo() + " TRANSFORMED");

        return exampleDto;
    }
}
