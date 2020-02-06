package com.humberco.transpojo.helper;

import com.humberco.transpojo.model.Transaction;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TestHelper {

    public Transaction createOneTransaction() {
            Random rnd = new Random();
            Integer timeOffset = rnd.nextInt(85000) + 1;
            Integer transactionLength = rnd.nextInt(600) + 1;
            LocalDateTime startTimeObject = generateTransactionStartTime(timeOffset);
            String startTime = startTimeObject.toString();
            String endTime = startTimeObject.plusSeconds(transactionLength).toString();
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
        return oneTransaction;
    }

    public List<Transaction> createTransactionList(int numberOfCopies) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for(int i = 0; i < numberOfCopies; i++) {
            Transaction oneTransaction = createOneTransaction();
            transactionList.add(oneTransaction);
        }
        return transactionList;
    }

    private static LocalDateTime generateTransactionStartTime(int timeOffset) {
        LocalDateTime transactionStartTime = LocalDate.now().atStartOfDay();
        return transactionStartTime.plusSeconds(timeOffset);
    }

    private String generateTransactionResultText(int transactionResult) {
        Random rnd = new Random();
        if(transactionResult == 1) {
            return "Exception";
        } else {
            return "Completed";
        }
    }
    private static int generateExceptionReason(int transactionResult) {
        if(transactionResult == 1) {
            Random rnd = new Random();
            return rnd.nextInt(20);
        } else {
            return 0;
        }
    }
}
