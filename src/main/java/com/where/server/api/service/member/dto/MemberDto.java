package com.where.server.api.service.member.dto;

import com.where.server.domain.member.MemberEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberDto {
    String name;
    String phoneNumber;
    String profileImage;
    Boolean isAppInstalled;
    Boolean isContactListSynchronized;

    public static MemberDto fromEntity(MemberEntity memberEntity){
        return MemberDto.builder()
                .name(memberEntity.getName())
                .phoneNumber(memberEntity.getPhoneNumber())
                .profileImage(memberEntity.getProfileImage())
                .isAppInstalled(memberEntity.getIsAppInstalled())
                .isContactListSynchronized(memberEntity.getIsContactListSynchronized())
                .build();
    }
}
