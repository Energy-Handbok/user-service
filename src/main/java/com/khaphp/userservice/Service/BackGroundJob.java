package com.khaphp.userservice.Service;

import com.khaphp.common.dto.notification.NotificationDTOcreate;
import com.khaphp.userservice.constant.Role;
import com.khaphp.userservice.entity.UserSystem;
import com.khaphp.userservice.repository.UserSystemRepository;
import com.khaphp.userservice.util.kafka.KafkaMessagePulisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class BackGroundJob {
    private final KafkaMessagePulisher kafkaMessagePulisher;
    private final UserSystemRepository userSystemRepository;

    @Scheduled(cron = "1 0 0 * * ?")
//    @Scheduled(cron = "10 * * * * *")   //test
    public void birthdayJob() {
        //format in db: 2001-01-01 01:01:01.000000
        //lấy date hiện tại ra
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DATE) + "";
        if(date.length() == 1) {
            date = "0" + date;
        }
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        if(month.length() == 1) {
            month = "0" + month;
        }
        String dateNow = month + "-" + date;
        System.out.println("check birthday: " + dateNow);
        //lấy hết user có ngày sinh hôm nay ra
        List<UserSystem> listCustomer = userSystemRepository.findByBirthdayByRole("%" + dateNow + "%", Role.CUSTOMER.toString());
        if(listCustomer.size() > 0){
            listCustomer.forEach(x -> {
                System.out.println(x.getName());
                kafkaMessagePulisher.sendNotificationDTOcreate(NotificationDTOcreate.builder()
                        .userId(x.getId())
                        .title("Chúc mừng sinh nhật")
                        .description("Energy HandBook chúc mừng sinh nhật " + x.getName())
                        .build());
            });
        }
    }
}