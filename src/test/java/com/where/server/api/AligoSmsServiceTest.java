package com.where.server.api;

import com.where.server.api.service.AligoSmsService;
import com.where.server.config.WebClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class AligoSmsServiceTest {

    @MockBean
    private WebClientConfig webClientConfig;

    @MockBean
    private WebClient webClient;

    @MockBean
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @MockBean
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @MockBean
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @MockBean
    private WebClient.ResponseSpec responseSpec;

    @Value("${aligo.api_key}")
    private String apiKey;

    private AligoSmsService aligoSmsService;

    @BeforeEach
    public void setUp() {
        aligoSmsService = new AligoSmsService(webClientConfig);
        when(webClientConfig.webClient()).thenReturn(webClient);
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn((WebClient.RequestBodySpec) requestHeadersUriSpec);
        when(requestBodyUriSpec.body(any(BodyInserters.FormInserter.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("response"));
    }

    @Test
    void testSendSms() {
        String msg = "Test message";
        String receiver = "01012345678";
        boolean isTest = true;

        aligoSmsService.sendSms(msg, receiver, isTest);

        ArgumentCaptor<BodyInserters.FormInserter<MultiValueMap<String, String>>> captor = ArgumentCaptor.forClass(BodyInserters.FormInserter.class);
        verify(requestBodyUriSpec).body(captor.capture());

//        MultiValueMap<String, String> formData = captor.getValue().build();
//        assertEquals(apiKey, formData.getFirst("key"));
//        assertEquals("cjs5241", formData.getFirst("user_id"));
//        assertEquals("01049051113", formData.getFirst("sender"));
//        assertEquals(receiver, formData.getFirst("receiver"));
//        assertEquals(msg, formData.getFirst("msg"));
//        assertEquals("인증번호 발신", formData.getFirst("title"));
//        assertEquals("Y", formData.getFirst("testmode_yn"));
    }

    @Test
    void testSendSmsErrorHandling() {
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new WebClientResponseException(500, "Internal Server Error", null, null, null)));

        String msg = "Test message";
        String receiver = "01012345678";
        boolean isTest = true;

        aligoSmsService.sendSms(msg, receiver, isTest);

        verify(responseSpec).bodyToMono(String.class);
    }
}
