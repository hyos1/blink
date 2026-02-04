package com.example.blink.service;

import com.example.blink.service.member.MemberService;
import com.example.blink.service.member.request.SignupCommand;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
@RequiredArgsConstructor
public class InitMember {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        SignupCommand signupCommand1 = new SignupCommand("notyourtype", "111@111.com", "111");
        SignupCommand signupCommand2 = new SignupCommand("daily.fade", "222@222.com", "222");
        SignupCommand signupCommand3 = new SignupCommand("cherryloop", "333@333.com", "333");
        SignupCommand signupCommand4 = new SignupCommand("peachybloom", "444@444.com", "444");
        SignupCommand signupCommand5 = new SignupCommand("barelyme", "555@555.com", "555");
        Long member1Id = memberService.save(signupCommand1);
        Long member2Id = memberService.save(signupCommand2);
        Long member3Id = memberService.save(signupCommand3);
        Long member4Id = memberService.save(signupCommand4);
        Long member5Id = memberService.save(signupCommand5);

        // 추가 회원 15명
        memberService.save(new SignupCommand("moonlight_sky", "moon@test.com", "password"));
        memberService.save(new SignupCommand("sunset_vibes", "sunset@test.com", "password"));
        memberService.save(new SignupCommand("ocean_breeze", "ocean@test.com", "password"));
        memberService.save(new SignupCommand("golden_hour", "golden@test.com", "password"));
        memberService.save(new SignupCommand("starry_night", "starry@test.com", "password"));

        memberService.save(new SignupCommand("coffee_addict", "coffee@test.com", "password"));
        memberService.save(new SignupCommand("bookworm_life", "book@test.com", "password"));
        memberService.save(new SignupCommand("fitness_freak", "fitness@test.com", "password"));
        memberService.save(new SignupCommand("foodie_explorer", "foodie@test.com", "password"));
        memberService.save(new SignupCommand("travel_junkie", "travel@test.com", "password"));

        memberService.save(new SignupCommand("music_lover", "music@test.com", "password"));
        memberService.save(new SignupCommand("art_enthusiast", "art@test.com", "password"));
        memberService.save(new SignupCommand("tech_geek", "tech@test.com", "password"));
        memberService.save(new SignupCommand("nature_soul", "nature@test.com", "password"));
        memberService.save(new SignupCommand("urban_explorer", "urban@test.com", "password"));

        memberService.save(new SignupCommand("vintage_style", "vintage@test.com", "password"));
        memberService.save(new SignupCommand("minimalist_life", "minimal@test.com", "password"));
        memberService.save(new SignupCommand("creative_mind", "creative@test.com", "password"));
        memberService.save(new SignupCommand("adventure_seeker", "adventure@test.com", "password"));
        memberService.save(new SignupCommand("dreamer_soul", "dreamer@test.com", "password"));
    }
}