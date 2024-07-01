//package com.where.api.core;
//
//import org.assertj.core.api.Assertions;
//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.junit.jupiter.api.Test;
//
//class JasyptConfigTest {
//    @Test
//    void jasypt(){
//
//        String encryptUrl = jasyptEncrypt(url);
//        String encryptUsername = jasyptEncrypt(username);
//        String encrypPassword = jasyptEncrypt(password);
//        String encrypJwt = jasyptEncrypt(jwt);
//
//        System.out.println("encryptUrl : " + encryptUrl);
//        System.out.println("encryptUsername : " + encryptUsername);
//        System.out.println("encrypPassword : " + encrypPassword);
//        System.out.println("encrypJwt : " + encrypJwt);
//
//        Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
//    }
//
//    private String jasyptEncrypt(String input) {
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        return encryptor.encrypt(input);
//    }
//
//    private String jasyptDecryt(String input){
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        return encryptor.decrypt(input);
//    }
//}
