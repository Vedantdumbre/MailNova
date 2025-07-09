package com.email.writer.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailGeneratorService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;


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

        // Return response

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
