package com.tlksolution;

import com.tlksolution.model.Page;
import com.tlksolution.repository.PageRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Autowired
	PageRepository pageRepository;
	@Bean
	@ConditionalOnProperty(prefix = "app", name = "db.init.enabled", havingValue = "true")
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Running.....");

			Page b1 = new Page("test");
			Page b2 = new Page("test page 2");
			Page b3 = new Page("test page 3");
			Page b4 = new Page("test page 4");
            b1.setIcon("😍");
			pageRepository.saveAll(List.of(b1, b2, b3, b4));
			b2.setParentPage(b1);
            b2.setIcon("😮");
			pageRepository.save(b2);
			b3.setParentPage(b2);
            b3.setIcon("👌");
			pageRepository.save(b3);
            b4.setIcon("😁");
            pageRepository.save(b4);
		};
	}
}
