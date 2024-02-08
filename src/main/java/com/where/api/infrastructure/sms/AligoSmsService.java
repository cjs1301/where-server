package com.where.api.infrastructure.sms;

import com.where.api.core.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;


@Slf4j
@Service
@RequiredArgsConstructor
public class AligoSmsService {
    private final WebClientConfig webClientConfig;
    private static final String API_URL = "https://apis.aligo.in/send/";
    @Value("${aligo.api_key}")
    private String apiKey;
    private static final String USER_ID = "cjs5241";
    private static final String SENDER = "01049051113";

    public void sendSms(String msg,String receiver,boolean isTest) {
        // Set up form data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("key", apiKey);
        formData.add("user_id", USER_ID);
        formData.add("sender", SENDER);
        formData.add("receiver", receiver);
        formData.add("msg", msg);
        formData.add("title", "인증번호 발신");
        formData.add("testmode_yn", isTest ? "Y": "N");


        // Perform the POST request
        webClientConfig.webClient().post()
                .uri(API_URL)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> log.debug("Response from Aligo API: " + response));
    }
}
