package com.example.Cacheable_and_CacheEvit_Annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheableAndCacheEvitAnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheableAndCacheEvitAnnotationApplication.class, args);
	}

}
