package com.bajaj.qualifier.service;

import com.bajaj.qualifier.model.WebhookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebhookService {

    private final WebClient webClient;

    @Value("${generate.url}")
    private String generateUrl;

    @Value("${submit.url}")
    private String submitUrl;

    @Value("${user.name}")
    private String name;

    @Value("${user.regNo}")
    private String regNo;

    @Value("${user.email}")
    private String email;

    public WebhookService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public WebhookResponse createWebhook() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("regNo", regNo);
        payload.put("email", email);

        return webClient.post()
                .uri(generateUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(WebhookResponse.class)
                .block(Duration.ofSeconds(30));
    }

    public boolean submitSolution(String webhook, String token, String finalQuery) {
        Map<String, String> body = new HashMap<>();
        body.put("finalQuery", finalQuery);

        try {
            webClient.post()
                    .uri(webhook)
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block(Duration.ofSeconds(20));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String pickQuestionUrl() {
        String digits = regNo.replaceAll("\\D", "");
        int lastTwo = digits.isEmpty() ? 0 : Integer.parseInt(digits.substring(digits.length() - 2));
        return lastTwo % 2 == 1
                ? "https://drive.google.com/file/d/1IeSI6l6KoSQAFfRihIT9tEDICtoz-G/view?usp=sharing"
                : "https://drive.google.com/file/d/143MR5cLFrlNEuHzzWJ5RHnEWuijuM9X/view?usp=sharing";
    }
}

