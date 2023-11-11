Feature: Place Order
  Scenario: Placing a transaction
    Given I have the following customer saved in the database
      | CustomerId  | DocumentNumber |
      | 1           | 12345678911    |
    When The user makes the following transaction
      | CustomerId  | OperationTypeId | Amount  |
      | 1           | 1               | 678.99  |
    Then The following transaction should be saved in the database
      | TransactionId | CustomerId      | OperationTypeId | Amount  |
      | 1             | 1               | 1               | -678.99 |
