# Software design (IS1220) Final Project
The goal of this project is to develop a software solution, called *MyFoodora*,
whose functionality are similar to that of nowadays food delivery systems.

The project consists of two parts, the **core** and the **interface**.

## To do
- [x] take password into account and explain why no use in first part
- [ ] explain the root manager idea (`root` as username)
- [ ] how to give info to user --> see `onDuty` and `offDuty` difference in `CommandProcessor` class

### CLUI methods
- [ ] `registerRestaurant <name> <address> <password>`
- [ ] `registerCustomer <firstName> <lastName> <username> <address> <password>`
- [ ] `registerCourier <firstName> <lastName> <username> <position> <password>`
- [x] `addDishRestaurantMenu <dishName> <dishCategory> <unitPrice>` --> we don't use restName as we have the login system
- [x] `login <username> <password>`
- [ ] `createMeal <mealName>` --> wait for new req.
- [ ] `addDish2Meal <dishName> <mealName>` --> wait for new req.
- [x] `showMeal <mealName>`
- [ ] `saveMeal <mealName>` --> wait for new req.
- [x] `setSpecialOffer <mealName>`
- [x] `removeFromSpecialOffer <mealName>`
- [ ] `addMeal2Order <mealName>` --> wait for new req.
- [ ] `endOrder <>` --> wait for new req. 
- [x] `onDuty <username>`
- [x] `offDuty <username>`
- [x] `associateCard <username> <cardType>`
- [x] `notifySpecialOffer <message> <mealName> <specialPrice>` --> deprecade see code
- [ ] `showMeals <orderingCriteria>` --> wait for new req.
- [ ] `showCourierDeliveries <>`
- [ ] `showRestaurantTop <>`
- [x] `showCustomers <> `
- [x] `showMenuItem <restaurant-name>`
- [ ] `showTotalProfit<>`
- [ ] `showTotalProfit<startDate> <endDate>`
- [x] `runTest <testScenario-file>`
- [x] `help <>`