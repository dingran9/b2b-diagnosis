package com.eeduspace.report.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
@Data
public class BaseResourceConfig {
    @Value("${baseresource.server}")
    private String server;
}
