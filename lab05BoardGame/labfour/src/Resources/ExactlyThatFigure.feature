Feature: Check if figure is skeleton or not
  Someone want to know what exactly that figure is

  Scenario Outline: It is skeleton or not
    Given This is "<figure>"
    When I ask you this is skeleton
    Then I should tell "<answer>"

    Examples:
      | figure | answer |
      | skeleton | Yes  |
      | golem    | No   |
      | orc      | No   |
