package com.bajaj.qualifier;

import com.bajaj.qualifier.model.WebhookResponse;
import com.bajaj.qualifier.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
    private final WebhookService webhookService;

    public MainApplication(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("▶ Starting webhook generation...");
        WebhookResponse response = webhookService.createWebhook();
        if (response == null || response.getWebhook() == null) {
            log.error("❌ Failed to generate webhook.");
            return;
        }

        log.info("✅ Got webhook: {}", response.getWebhook());
        log.info("Assigned Question URL: {}", webhookService.pickQuestionUrl());

        // Replace this with your actual SQL
        String finalQuery = "SELECT * FROM employees WHERE salary > 50000;";

        log.info("Submitting finalQuery...");
        boolean ok = webhookService.submitSolution(response.getWebhook(), response.getAccessToken(), finalQuery);

        if (ok) log.info("🎯 Submission successful!");
        else log.error("🚨 Submission failed!");
    }

   
}

