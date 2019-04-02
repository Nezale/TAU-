  Scenario: Someone wants to delete skeleton from the figure list

  Given Someone is in front of figure menage panel
    When  Someone click delete button under the "skeleton" name
    And   Skeleton HP is equal to "50"
    Then  skeleton has been successfully deleted from the list