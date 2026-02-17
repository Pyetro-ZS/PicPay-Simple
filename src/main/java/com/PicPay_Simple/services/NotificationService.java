package com.PicPay_Simple.services;

import com.PicPay_Simple.domain.user.User;
import com.PicPay_Simple.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("http://o4d9z.mockylab.io/notify", notificationRequest, String.class);
//
//        if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
//            System.out.println("Erro ao enviar notificação");
//            throw new RuntimeException("Serviço de notificação fora do ar.");
//        }
        System.out.println("Notificação enviada para o usuário ");
    }
}
