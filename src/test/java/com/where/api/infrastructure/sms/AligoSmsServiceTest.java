package com.where.api.infrastructure.sms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "test")
class AligoSmsServiceTest {

    @Autowired
    private AligoSmsService aligoSmsService;

    @Test
    void sendSMS() {
        // 테스트 데이터
        String msg = "Test Message";
        String receiver = "01012345678";
        boolean isTest = true;

        // 테스트 메서드 호출
        aligoSmsService.sendSms(msg, receiver, isTest);
    }

}
