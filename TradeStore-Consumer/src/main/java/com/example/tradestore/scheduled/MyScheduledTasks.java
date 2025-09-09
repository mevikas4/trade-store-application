package com.example.tradestore.scheduled;
import com.example.tradestore.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {

    @Autowired
    private TradeService tradeService;

    @Scheduled(fixedRate = 10000) // Runs every 5 seconds
    public void runFixedRateTask() {
        System.out.println("Fixed Rate Task executed!");
        tradeService.updateExpiredTrades();
    }

   /* @Scheduled(cron = "0 0 10 * * ?") // Runs every day at 10:00 AM
    public void runCronTask() {
        System.out.println("Cron Task executed!");
        tradeService.updateExpiredTrades();
    }*/
}
