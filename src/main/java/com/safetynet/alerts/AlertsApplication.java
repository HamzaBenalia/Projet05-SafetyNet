package com.safetynet.alerts;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.loadData.InitData;
import com.safetynet.alerts.service.DataPopulatorService;
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
    private DataPopulatorService dataPopulatorService;

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dataPopulatorService.loadData(objectMapper.readValue(dataResourceFile.getFile(), InitData.class));
    }
}
