package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.BaseballGame;
import com.github.alfabravo2013.entities.Cubs;
import com.github.alfabravo2013.entities.Game;
import com.github.alfabravo2013.entities.RedSox;
import com.github.alfabravo2013.entities.Team;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import(InfrastructureConfig.class)
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
    public Game game(DataSource dataSource) {
        var game = new BaseballGame(redSox(), cubs());
        game.setDataSource(dataSource);
        return game;
    }
}
