Feature: Search PedidosYa

  Background:

    Given user navigates to https://www.pedidosya.com.uy/
    And set the address

    Scenario Outline: Pizza results

      Given user search restaurant <type> and report the amount of results on the page
      When filter by <filter> and report the amount of results on the page
      And order the results by 'Mejor puntuados' and validate it
      And select the first result
      Then User views restaurant important information

      Examples:
      |type |filter    |
      |Pizza|Descuentos|