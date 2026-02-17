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

        memberService.save(new SignupCommand("night_runner", "night@test.com", "password"));
        memberService.save(new SignupCommand("early_bird", "early@test.com", "password"));
        memberService.save(new SignupCommand("lazy_sunday", "lazy@test.com", "password"));
        memberService.save(new SignupCommand("daily_motivation", "motivation@test.com", "password"));
        memberService.save(new SignupCommand("code_master", "code@test.com", "password"));

        memberService.save(new SignupCommand("bug_hunter", "bug@test.com", "password"));
        memberService.save(new SignupCommand("backend_dev", "backend@test.com", "password"));
        memberService.save(new SignupCommand("frontend_guru", "frontend@test.com", "password"));
        memberService.save(new SignupCommand("fullstack_dream", "fullstack@test.com", "password"));
        memberService.save(new SignupCommand("dev_life", "devlife@test.com", "password"));

        memberService.save(new SignupCommand("design_thinker", "design@test.com", "password"));
        memberService.save(new SignupCommand("pixel_artist", "pixel@test.com", "password"));
        memberService.save(new SignupCommand("ui_lover", "ui@test.com", "password"));
        memberService.save(new SignupCommand("ux_journey", "ux@test.com", "password"));
        memberService.save(new SignupCommand("creative_flow", "flow@test.com", "password"));

        memberService.save(new SignupCommand("morning_coffee", "morning@test.com", "password"));
        memberService.save(new SignupCommand("late_night_coder", "latenight@test.com", "password"));
        memberService.save(new SignupCommand("focus_mode", "focus@test.com", "password"));
        memberService.save(new SignupCommand("deep_thinker", "deep@test.com", "password"));
        memberService.save(new SignupCommand("idea_factory", "idea@test.com", "password"));

        memberService.save(new SignupCommand("simple_habit", "habit@test.com", "password"));
        memberService.save(new SignupCommand("healthy_choice", "healthy@test.com", "password"));
        memberService.save(new SignupCommand("mindful_life", "mindful@test.com", "password"));
        memberService.save(new SignupCommand("balanced_day", "balanced@test.com", "password"));
        memberService.save(new SignupCommand("calm_moment", "calm@test.com", "password"));

        memberService.save(new SignupCommand("road_trip", "road@test.com", "password"));
        memberService.save(new SignupCommand("city_walker", "city@test.com", "password"));
        memberService.save(new SignupCommand("mountain_lover", "mountain@test.com", "password"));
        memberService.save(new SignupCommand("sea_side", "sea@test.com", "password"));
        memberService.save(new SignupCommand("wander_daily", "wander@test.com", "password"));

        memberService.save(new SignupCommand("film_addict", "film@test.com", "password"));
        memberService.save(new SignupCommand("series_binger", "series@test.com", "password"));
        memberService.save(new SignupCommand("documentary_fan", "docu@test.com", "password"));
        memberService.save(new SignupCommand("cinema_night", "cinema@test.com", "password"));
        memberService.save(new SignupCommand("popcorn_time", "popcorn@test.com", "password"));

        memberService.save(new SignupCommand("gamer_mode", "gamer@test.com", "password"));
        memberService.save(new SignupCommand("retro_player", "retro@test.com", "password"));
        memberService.save(new SignupCommand("console_lover", "console@test.com", "password"));
        memberService.save(new SignupCommand("pc_builder", "pc@test.com", "password"));
        memberService.save(new SignupCommand("pixel_runner", "pixelrun@test.com", "password"));

        memberService.save(new SignupCommand("study_hard", "study@test.com", "password"));
        memberService.save(new SignupCommand("note_taker", "note@test.com", "password"));
        memberService.save(new SignupCommand("planner_life", "planner@test.com", "password"));
        memberService.save(new SignupCommand("goal_setter", "goal@test.com", "password"));
        memberService.save(new SignupCommand("future_builder", "future@test.com", "password"));

        memberService.save(new SignupCommand("sunrise_walk", "sunrise@test.com", "password"));
        memberService.save(new SignupCommand("evening_chill", "evening@test.com", "password"));
        memberService.save(new SignupCommand("night_owl", "owl@test.com", "password"));
        memberService.save(new SignupCommand("quiet_reader", "quiet@test.com", "password"));
        memberService.save(new SignupCommand("slow_living", "slow@test.com", "password"));

        memberService.save(new SignupCommand("daily_writer", "writer@test.com", "password"));
        memberService.save(new SignupCommand("journal_keeper", "journal@test.com", "password"));
        memberService.save(new SignupCommand("story_maker", "story@test.com", "password"));
        memberService.save(new SignupCommand("word_collector", "word@test.com", "password"));
        memberService.save(new SignupCommand("thought_stream", "thought@test.com", "password"));

        memberService.save(new SignupCommand("morning_routine", "routine@test.com", "password"));
        memberService.save(new SignupCommand("daily_steps", "steps@test.com", "password"));
        memberService.save(new SignupCommand("habit_tracker", "tracker@test.com", "password"));
        memberService.save(new SignupCommand("self_growth", "growth@test.com", "password"));
        memberService.save(new SignupCommand("positive_mind", "positive@test.com", "password"));

        memberService.save(new SignupCommand("creative_space", "space@test.com", "password"));
        memberService.save(new SignupCommand("idea_note", "ideanote@test.com", "password"));
        memberService.save(new SignupCommand("brain_storm", "brain@test.com", "password"));
        memberService.save(new SignupCommand("focus_daily", "focusdaily@test.com", "password"));
        memberService.save(new SignupCommand("task_crusher", "task@test.com", "password"));

        memberService.save(new SignupCommand("life_optimizer", "optimize@test.com", "password"));
        memberService.save(new SignupCommand("time_manager", "time@test.com", "password"));
        memberService.save(new SignupCommand("priority_first", "priority@test.com", "password"));
        memberService.save(new SignupCommand("work_balance", "balancework@test.com", "password"));
        memberService.save(new SignupCommand("energy_saver", "energy@test.com", "password"));

        memberService.save(new SignupCommand("daily_log", "log@test.com", "password"));
        memberService.save(new SignupCommand("memory_keeper", "memory@test.com", "password"));
        memberService.save(new SignupCommand("moment_collector", "moment@test.com", "password"));
        memberService.save(new SignupCommand("simple_record", "record@test.com", "password"));
        memberService.save(new SignupCommand("life_snapshot", "snapshot@test.com", "password"));

    }
}