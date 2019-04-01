Feature: GetFigureByRegexFeature
  This method have to find figure in the list by regular expression

  Scenario: Someone want to find a figure, but i remember only a part of name

    Given Someone is in front of the search bar
    When Someone put "gol" phrase into the search bar
    Then Someone finds the figure he wanted to find and he/she is very happy :)