@tag
Feature: Title of your feature
  I want to use this template for my feature file
  
  Background:
  	Given I launch the application

  @Positive
  Scenario Outline: 
    Given I login to the app with <username> and <password>
    When I add the product <product>
    And I checkout product <product> and submit the order for country "India"
    Then I capture orderIds for <product>
    Then I close the application

    Examples: 
      | username  									| password 							| product 									|
      | henrycavillpookie@gmail.com | RahulShetty@1295     				| ZARA COAT 3;IPHONE 13 PRO	|
    #  | hughjackmanpookie@gmail.com | RahulShetty@1235     	| ZARA COAT 3;IPHONE 13 PRO	|
    
  @Negative
  Scenario Outline: 
    Given I login to the app with <username> and <password>
    Then I validate the error message in the login page
    Then I close the application

    Examples: 
      | username  									| password 							|
      | henrycavillpookie@gmail.com | Incorrect    					| 
    #  | hughjackmanpookie@gmail.com | RahulShetty@1235     	| ZARA COAT 3;IPHONE 13 PRO	|
