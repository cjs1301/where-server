package org.where.moduleapi.api.service.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.member.MemberEntity;

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
