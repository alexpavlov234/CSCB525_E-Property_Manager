package com.nbu.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nbu.dto.PaymentTaxDto;

import java.io.File;
import java.io.IOException;

public class PaymentFileWriter {

    private static final String PAYMENTS_DIR = "payments";
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static void saveToJsonFile(PaymentTaxDto paymentTaxDto) throws IOException {
        String fileName = String.format("payment_%d_%s.json",
                paymentTaxDto.getId(),
                paymentTaxDto.getPaymentDate().toString());

        File file = new File(PAYMENTS_DIR, fileName);
        file.getParentFile().mkdirs();

        mapper.writeValue(file, paymentTaxDto);
    }
}

