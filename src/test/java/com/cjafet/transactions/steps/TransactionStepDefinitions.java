package com.cjafet.transactions.steps;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.TransactionRequest;
import com.cjafet.transactions.domain.transaction.Transaction;
import com.cjafet.transactions.repository.CustomerRepository;
import com.cjafet.transactions.repository.TransactionRepository;
import com.cjafet.transactions.service.TransactionService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;

public class TransactionStepDefinitions {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionService transactionService;
    @Given("I have the following customer saved in the database")
    public void i_have_available_items_in_stock(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        String documentNumber = rows.get(1).get(1);
        Customer customer = Customer.builder()
                .documentNumber(documentNumber)
                .build();
        customerRepository.save(customer);
        assertThat(customerRepository.count()).isEqualTo(1);
    }
    @When("The user makes the following transaction")
    public void an_user_place_an_order_for_one_item(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        Long customerId = Long.valueOf(rows.get(1).get(0));
        Integer operationTypeId = Integer.parseInt(rows.get(1).get(1));
        Double amount = Double.valueOf(rows.get(1).get(2));

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .customerID(customerId)
                .operationTypeID(operationTypeId)
                .amount(amount)
                .build();
        transactionService.addTransaction(transactionRequest);
        assertThat(transactionRepository.count()).isEqualTo(1);

    }
    @Then("The following transaction should be saved in the database")
    public void one_item_should_be_removed_from_my_inventory(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);
        Long txId = Long.valueOf(rows.get(1).get(0));
        Long customerId = Long.valueOf(rows.get(1).get(1));
        Integer operationTypeId = Integer.parseInt(rows.get(1).get(2));
        Double amount = Double.valueOf(rows.get(1).get(3));

        Optional<Transaction> tx = transactionRepository.findById(txId);
        if(tx.isPresent()) {
            assertThat(tx.get().getTransactionID()).isEqualTo(txId);
            assertThat(tx.get().getCustomer().getCustomerID()).isEqualTo(customerId);
            assertThat(tx.get().getOperationTypeID()).isEqualTo(operationTypeId);
            assertThat(tx.get().getAmount()).isEqualTo(amount);
        }
    }

}
