package org.where.moduleapi.api.service.follow;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

public class FollowRelationDto {
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CreateList {
        List<String> phoneNumberList;
    }
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Create {
        String phoneNumber;
    }
}
