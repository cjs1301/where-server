//package com.where.server.config;
//import com.where.server.domain.friend.FollowRelationEntity;
//import com.where.server.domain.member.MemberEntity;
//import com.where.server.domain.member.MemberRepository;
//import com.where.server.domain.member.MemberRole;
//import com.where.server.domain.channel.ChannelEntity;
//import com.where.server.domain.channel.ChannelRepository;
//import com.where.server.domain.channel.FollowChannelEntity;
//import com.where.server.domain.channel.FollowChannelRepository;
//import com.where.server.domain.channel.LocationEntity;
//import com.where.server.domain.channel.LocationRepository;
//import com.where.server.domain.channel.MessageEntity;
//import com.where.server.domain.channel.MessageRepository;
//import com.where.server.domain.friend.FollowRelationRepository;
//import org.locationtech.jts.geom.Coordinate;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.*;
//@Configuration
////@Profile("dev")
//public class DummyDataInitializer {
//
//    @Bean
//    public CommandLineRunner initDummyData(
//            MemberRepository memberRepository,
//            ChannelRepository channelRepository,
//            FollowChannelRepository followChannelRepository,
//            LocationRepository locationRepository,
//            MessageRepository messageRepository,
//            FollowRelationRepository followMemberRepository) {
//        return args -> {
//            // 기존 데이터 삭제
////            messageRepository.deleteAll();
////            locationRepository.deleteAll();
////            followChannelRepository.deleteAll();
////            followMemberRepository.deleteAll();
////            channelRepository.deleteAll();
////            memberRepository.deleteAll();
//
//            // 멤버 더미 데이터 생성
//            List<MemberEntity> members = createDummyMembers();
//            memberRepository.saveAll(members);
//
//            // 채널 더미 데이터 생성
//            List<ChannelEntity> channels = createDummyChannels();
//            channelRepository.saveAll(channels);
//
//            // 팔로우 채널 더미 데이터 생성
//            List<FollowChannelEntity> followChannels = createDummyFollowChannels(members, channels);
//            followChannelRepository.saveAll(followChannels);
//
//            // 위치 더미 데이터 생성
//            List<LocationEntity> locations = createDummyLocations(members, channels);
//            locationRepository.saveAll(locations);
//
//            // 메시지 더미 데이터 생성
//            List<MessageEntity> messages = createDummyMessages(members, channels);
//            messageRepository.saveAll(messages);
//
//            // 팔로우 멤버 더미 데이터 생성
//            List<FollowRelationEntity> followMembers = createDummyFollowMembers(members);
//            followMemberRepository.saveAll(followMembers);
//
//            System.out.println("Dummy data initialized successfully.");
//        };
//    }
//
//    private List<MemberEntity> createDummyMembers() {
//        return Arrays.asList(
//                MemberEntity.builder()
//                        .phoneNumber("+821012345678")
//                        .name("User 1")
//                        .isEnabled(true)
//                        .isAppInstalled(true)
//                        .isContactListSynchronized(true)
//                        .role(MemberRole.ROLE_USER)
//                        .profileImage("https://example.com/user1.jpg")
//                        .build(),
//                MemberEntity.builder()
//                        .phoneNumber("+821087654321")
//                        .name("User 2")
//                        .isEnabled(true)
//                        .isAppInstalled(true)
//                        .isContactListSynchronized(false)
//                        .role(MemberRole.ROLE_USER)
//                        .profileImage("https://example.com/user2.jpg")
//                        .build(),
//                MemberEntity.builder()
//                        .phoneNumber("+821000000000")
//                        .name("Admin")
//                        .isEnabled(true)
//                        .isAppInstalled(true)
//                        .isContactListSynchronized(true)
//                        .role(MemberRole.ROLE_TEST)
//                        .profileImage("https://example.com/admin.jpg")
//                        .build()
//        );
//    }
//
//    private List<ChannelEntity> createDummyChannels() {
//        return Arrays.asList(
//                ChannelEntity.builder()
//                        .name("Channel 1")
//                        .build(),
//                ChannelEntity.builder()
//                        .name("Channel 2")
//                        .build()
//        );
//    }
//
//    private List<FollowChannelEntity> createDummyFollowChannels(List<MemberEntity> members, List<ChannelEntity> channels) {
//        List<FollowChannelEntity> followChannels = new ArrayList<>();
//        for (MemberEntity member : members) {
//            for (ChannelEntity channel : channels) {
//                followChannels.add(FollowChannelEntity.builder()
//                        .member(member)
//                        .channel(channel)
//                        .build());
//            }
//        }
//        return followChannels;
//    }
//
//    private List<LocationEntity> createDummyLocations(List<MemberEntity> members, List<ChannelEntity> channels) {
//        List<LocationEntity> locations = new ArrayList<>();
//        Random random = new Random();
//
//        for (MemberEntity member : members) {
//            for (ChannelEntity channel : channels) {
//                List<Coordinate> coordinates = new ArrayList<>();
//                for (int i = 0; i < 5; i++) {
//                    double lat = 35 + random.nextDouble() * 5;
//                    double lon = 125 + random.nextDouble() * 5;
//                    coordinates.add(new Coordinate(lon, lat));
//                }
//                locations.add(LocationEntity.builder()
//                        .coordinates(coordinates)
//                        .channel(channel)
//                        .member(member)
//                        .build());
//            }
//        }
//        return locations;
//    }
//
//    private List<MessageEntity> createDummyMessages(List<MemberEntity> members, List<ChannelEntity> channels) {
//        List<MessageEntity> messages = new ArrayList<>();
//        Random random = new Random();
//        String[] dummyMessages = {"Hello!", "How are you?", "What's up?", "Nice weather today!", "Let's meet up!"};
//
//        for (ChannelEntity channel : channels) {
//            for (int i = 0; i < 5; i++) {
//                messages.add(MessageEntity.builder()
//                        .message(dummyMessages[random.nextInt(dummyMessages.length)])
//                        .channel(channel)
//                        .member(members.get(random.nextInt(members.size())))
//                        .build());
//            }
//        }
//        return messages;
//    }
//
//    private List<FollowRelationEntity> createDummyFollowMembers(List<MemberEntity> members) {
//        List<FollowRelationEntity> followMembers = new ArrayList<>();
//        for (int i = 0; i < members.size(); i++) {
//            for (int j = 0; j < members.size(); j++) {
//                if (i != j) {
//                    followMembers.add(FollowRelationEntity.builder()
//                            .follower(members.get(i))
//                            .following(members.get(j))
//                            .build());
//                }
//            }
//        }
//        return followMembers;
//    }
//}
