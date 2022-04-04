package com.treeshop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Path productUploadDir = Paths.get("./src/main/resources/static/product-imgs/");
        Path productUploadDir = Paths.get("./dynamic-resources/product-imgs/");
        String productUploadPath = productUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/dynamic-resources/product-imgs/**").addResourceLocations("file:/" + productUploadPath +"/");
    }
}
