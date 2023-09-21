package dev.drew.restaurantreview.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class BlacklistTokenCleanUp {

    @Autowired
    private JwtBlacklistRepository jwtBlacklistRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void cleanUpExpiredTokens() {
        // Convert LocalDateTime to Date
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        jwtBlacklistRepository.deleteExpiredTokens(now);
    }
}
