package com.email.writer.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailGeneratorService(WebClient webClient) {
        this.webClient = webClient;
    }


    public String generateEmailReply(EmailRequest emailRequest) {
        //Build a prompt
        String prompt = buildPrompt(emailRequest);

        // Craft a request
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                            Map.of("text", prompt)
                        })
                }
        );

        //DO request and get response
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        // Extract Response Return
        return extractResponseContent(response);

    }

    private String extractResponseContent(String response) {
        try{

        } catch (Exception e) {
            return "Error processing response: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional reply for the following email. Please don't generate the subject line.");
        if(emailRequest.getTone() != null  && !emailRequest.getTone().isEmpty()) {
            prompt.append("Use a ").append(emailRequest.getTone()).append("tone"); // condition for adding in a tone(by default is professional)
        }

        prompt.append("\nOriginal email:\n").append(emailRequest.getEmailContent()); // returning the output of the prompt given above👍
        return prompt.toString();
    }
}
