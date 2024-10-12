package org.where.moduleapi.api.service.member.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.member.MemberEntity;

import java.util.Set;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberDto {
    Long id;
    String name;
    String phoneNumber;
    String profileImage;
    Boolean isContactListSynchronized;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update{
        private String name;
        private String profileImage;
    }

    public static MemberDto fromEntity(MemberEntity memberEntity){
        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .phoneNumber(memberEntity.getPhoneNumber())
                .profileImage(memberEntity.getProfileImage())
                .isContactListSynchronized(memberEntity.getIsContactListSynchronized())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Contact {
        private Set<String> phoneNumberList;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        MemberDto memberDto = (MemberDto) o;
//        return Objects.equals(id, memberDto.id) && Objects.equals(name, memberDto.name) && Objects.equals(phoneNumber, memberDto.phoneNumber) && Objects.equals(profileImage, memberDto.profileImage) && Objects.equals(isContactListSynchronized, memberDto.isContactListSynchronized);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, phoneNumber, profileImage, isContactListSynchronized);
//    }
}
