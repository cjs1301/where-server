package com.where.api.domain.member.service;

import com.where.api.domain.member.entity.MemberEntity;
import com.where.api.domain.member.entity.MemberRole;
import com.where.api.domain.member.repository.MemberRepository;
import com.where.api.infrastructure.sms.AligoSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AligoSmsService aligoSmsService;


    public String createRandomNumber(){
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            numStr.append(rand.nextInt(10));
        }

        return numStr.toString();
    }
    public void mobileNumberCertificationProcess(String mobileNumber){
        String randomNumber = this.createRandomNumber();
        Boolean isExist = memberRepository.existsByMobile(mobileNumber);

        if (Boolean.TRUE.equals(isExist)) {
            MemberEntity member = memberRepository.findByMobile(mobileNumber);
            member.setPassword(bCryptPasswordEncoder.encode(randomNumber));
            memberRepository.save(member);
        } else {
            MemberEntity member = new MemberEntity();
            member.setMobile(mobileNumber);
            member.setPassword(bCryptPasswordEncoder.encode(randomNumber));
            member.setRole(MemberRole.ROLE_USER);
            member.setEnabled(true);
            memberRepository.save(member);
        }
        String msg = "[오디야?] 본인확인 인증번호 \n" +
                "["+randomNumber+"]를 화면에 입력해주세요";
        aligoSmsService.sendSms(msg,mobileNumber,false);
    }
}
