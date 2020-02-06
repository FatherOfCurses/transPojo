package com.humberco.transpojo;

import com.humberco.transpojo.model.Transaction;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for (int i = 0; i < 300; i++) {
            Random rnd = new Random();
            Integer timeOffset = rnd.nextInt(85000) + 1;
            Integer transactionLength = rnd.nextInt(600) + 1;
            LocalDateTime startTimeObject = generateTransactionStartTime(timeOffset);
            LocalDateTime endTimeObject = startTimeObject.plusSeconds(transactionLength);
            String startTime = startTimeObject.toString();
            String endTime = endTimeObject.toString();
            int transactionResult = rnd.nextInt(2);
            Transaction oneTransaction = Transaction.builder()
                    .transid(RandomStringUtils.randomAlphanumeric(10))
                    .starttime(startTime)
                    .endtime(endTime)
                    .translength(transactionLength)
                    .result(generateTransactionResultText(transactionResult))
                    .exceptionreason(generateExceptionReason(transactionResult))
                    .client(rnd.nextInt(20))
                    .build();
            System.out.println("I generated transaction number " + i);
            transactionList.add(oneTransaction);
        }
//        for (Transaction transaction : transactionList) {
//            transactionRepositoryImpl.save(transaction);
//        }
    }

    private static LocalDateTime generateTransactionStartTime(int timeOffset) {
        LocalDateTime transactionStartTime = LocalDate.now().atStartOfDay();
        return transactionStartTime.plusSeconds(timeOffset);
    }

    private String generateTransactionResultText(int transactionResult) {
        Random rnd = new Random();
        if (transactionResult == 1) {
            return "Exception";
        } else {
            return "Completed";
        }
    }

    private static int generateExceptionReason(int transactionResult) {
        if (transactionResult == 1) {
            Random rnd = new Random();
            return rnd.nextInt(20);
        } else {
            return 0;
        }
    }


}

