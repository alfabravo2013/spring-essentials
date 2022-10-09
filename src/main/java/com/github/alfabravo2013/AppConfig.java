package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.BaseballGame;
import com.github.alfabravo2013.entities.Cubs;
import com.github.alfabravo2013.entities.Game;
import com.github.alfabravo2013.entities.RedSox;
import com.github.alfabravo2013.entities.Team;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Team redSox() {
        return new RedSox();
    }

    @Bean
    public Team cubs() {
        return new Cubs();
    }

    @Bean
    public Game game() {
        return new BaseballGame(redSox(), cubs());
    }
}
