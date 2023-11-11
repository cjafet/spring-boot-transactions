package com.cjafet.transactions.runners;

import com.cjafet.transactions.TransactionsApplication;
import com.cjafet.transactions.repository.CustomerRepository;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        glue = "com.cjafet.transactions.steps",
        features = "classpath:features")
@CucumberContextConfiguration
@SpringBootTest(classes = {TransactionsApplication.class, CustomerRepository.class})
public class TransactionTestRunner {
}
