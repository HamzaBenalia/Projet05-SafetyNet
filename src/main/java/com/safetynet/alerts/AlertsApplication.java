package com.safetynet.alerts;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.loadData.InitData;
import com.safetynet.alerts.service.impl.DataPopulatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class AlertsApplication implements CommandLineRunner {

    @Value("classpath:data.json")
    private Resource dataResourceFile;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataPopulatorServiceImpl dataPopulatorServiceImpl;

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dataPopulatorServiceImpl.loadData(objectMapper.readValue(dataResourceFile.getFile(), InitData.class));
    }
}
