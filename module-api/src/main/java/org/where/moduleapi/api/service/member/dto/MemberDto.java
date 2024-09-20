package org.where.moduleapi.api.service.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.member.MemberEntity;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberDto {
    Long id;
    String name;
    String phoneNumber;
    String profileImage;
    Boolean isRegistered;
    Boolean isContactListSynchronized;

    public static MemberDto fromEntity(MemberEntity memberEntity){
        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .phoneNumber(memberEntity.getPhoneNumber())
                .profileImage(memberEntity.getProfileImage())
                .isRegistered(memberEntity.getIsRegistered())
                .isContactListSynchronized(memberEntity.getIsContactListSynchronized())
                .build();
    }

    @Getter
    public static class PhoneNumberList {
        List<String> phoneNumberList;
    }
}
