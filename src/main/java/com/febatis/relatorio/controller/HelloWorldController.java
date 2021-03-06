package com.febatis.relatorio.controller;

import com.febatis.relatorio.enums.PaymentType;
import com.febatis.relatorio.model.Greeting;
import com.febatis.relatorio.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

import static net.logstash.logback.argument.StructuredArguments.value;

@Controller
public class HelloWorldController {

    private static final Logger logger
            = LoggerFactory.getLogger(HelloWorldController.class);

    private static final String template = "Hello, %s!";
    private static final String templateType = "ReportType: %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        logger.info("Example log from {}", HelloWorldController.class.getSimpleName());
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/throw-exception")
    @ResponseBody
    public Greeting throwException(@RequestParam(name = "name", required = false, defaultValue = "Estranho") String name) {
        throw new RuntimeException("Ocorreu erro: " + name);
    }

    @PostMapping("/make-general-report")
    @ResponseBody
    public Greeting makeGeneralReport() {
        logger.info("Report Type: {}", value("ReportType", "General"));
        return new Greeting(counter.incrementAndGet(), String.format(templateType, "General"));
    }

    @PostMapping("/make-personal-report/{paymentType}/{throwError}")
    @ResponseBody
    public Greeting makePersonalReport(@PathVariable(required = false) PaymentType paymentType, @PathVariable boolean throwError) {

        if(throwError) {
            throw new RuntimeException("Ocorreu erro chato");
        }

        logger.info("Report Type: {}", value("ReportType", "PERSONAL " + paymentType));
        return new Greeting(counter.incrementAndGet(), String.format(templateType, "PERSONAL " + paymentType));
    }

    @PostMapping("/make-personal-report/complete")
    @ResponseBody
    public Greeting makePersonalReport() {
        logger.info("Report Type: {}", value("ReportType", "PERSONAL COMPLETE"));
        return new Greeting(counter.incrementAndGet(), String.format(templateType, "PERSONAL COMPLETE"));
    }

    @PostMapping("/statement")
    @ResponseBody
    public Payment[] getStatement() {
        logger.info("Report Type: {}", value("ReportType", "STATEMENT"));

        final String uri = "http://localhost:8080/pagamentos/pay/find-all";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Payment[].class);

    }

}
