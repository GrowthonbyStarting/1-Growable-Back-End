package com.growable.starting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Payment;
import com.growable.starting.model.PaymentRequest;
import com.growable.starting.repository.MenteeRepository;
import com.growable.starting.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final MenteeRepository menteeRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${import.imp_key}")
    private String keyPg;
    @Value("${import.imp_secret}")
    private String secretPg;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository, MenteeRepository menteeRepository) {
        this.paymentRepository = paymentRepository;
        this.menteeRepository = menteeRepository;
    }

    public void setPayment(String merchantUid, int amount, String buyerEmail) {
        Payment payment = new Payment();
        payment.setBuyerEmail(buyerEmail);
        payment.setMerchant_uid(merchantUid);
        payment.setAmount(amount);
        paymentRepository.save(payment);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            logger.info("Payment Request: {}", paymentRequest);
            String buyerEmail = paymentRequest.getBuyerEmail();
            String accessToken = getAccessToken();
            String merchantUid = paymentRequest.getMerchant_uid();
            int amount = paymentRequest.getAmount();
            String impUid = paymentRequest.getImp_uid();
            int resAmount = (int) getPaymentData(impUid, accessToken);
            if (amount == resAmount) {
                setPayment(merchantUid, amount, buyerEmail);
                Optional<Mentee> optionalMentee = menteeRepository.findByEmail(buyerEmail);
                if (optionalMentee.isPresent()) {
                    Mentee mentee = optionalMentee.get();
                    mentee.setPoint(mentee.getPoint() + amount);
                    menteeRepository.save(mentee);
                    return new ResponseEntity<>(mentee.getPoint(), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            logger.error("Payment Error: {}", e.getMessage(), e);
            return new ResponseEntity<>("결제 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("결제 취소", HttpStatus.FORBIDDEN);
    }

    public String getAccessToken() {
        final String url = "https://api.iamport.kr/users/getToken";
        final String impApiKey = keyPg;
        final String impApiSecret = secretPg;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("imp_key", impApiKey);
        bodyParams.put("imp_secret", impApiSecret);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(bodyParams, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseMap = objectMapper.readValue(responseEntity.getBody(), Map.class);
                return (String) ((Map<String, Object>) responseMap.get("response")).get("access_token");
            } catch (Exception e) {
                // 예외 처리 로직 (예: 로깅)
                return null;
            }
        } else {
            System.out.println("key 인증 실패");
            return null;
        }
    }

    public ResponseEntity<String> callPortOneServer(String url, HttpMethod method, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, requestEntity, String.class);
    }

    public Object getPaymentData(String impUid, String accessToken) throws JsonProcessingException {
        final String url = "https://api.iamport.kr/payments/" + impUid;
        HttpMethod method = HttpMethod.GET;

        ResponseEntity<String> responseEntity = callPortOneServer(url, method, accessToken);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objMapper = new ObjectMapper();
            Map<String, Object> responseMap = objMapper.readValue(responseEntity.getBody(), Map.class);
            return ((Map<String, Object>) responseMap.get("response")).get("amount");
        } else if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            System.out.println("인증 토큰 불량");
            return null;
        }
        return 0;
    }
}

