# Software design (IS1220) Final Project
The goal of this project is to develop a software solution, called *MyFoodora*,
whose functionality are similar to that of nowadays food delivery systems.

The project consists of two parts, the **core** and the **interface**.

## To do
- [ ] take password into account and explain why no use in first part
- [ ] explain the root manager idea (`root` as username)
- [ ] how to give info to user --> see `onDuty` and `offDuty` difference in `CommandProcessor` class

### CLUI methods
- [x] `login <username, password>`
- [ ] `createMeal <mealName>`
- [ ] `addDish2Meal <dishName, mealName>`
- [x] `showMeal <mealName>`
- [ ] `saveMeal <mealName>`
- [ ] `setMealPrice <mealName>`
- [x] `setSpecialOffer <mealName>`
- [x] `removeFromSpecialOffer <mealName>`
- [ ] `addDish <dishName, dishCategory, unitPrice>`
- [ ] `addMeal2Order <mealName>`
- [ ] `endOrder <>` 
- [ ] `registerClient <firstName, lastName, username, address, password>` --> wait for new req.
- [ ] `registerCourier <firstName, lastName, username, position, password>` --> wait for new req.
- [x] `onDuty <username>`
- [x] `offDuty <username>`
- [ ] `addContactInfo <contactInfo>` --> wait for new req.
- [x] `associateCard <username, cardType>`
- [x] `associateAgreement <username, agreement>`
- [x] `registerRestaurant <name, username, address, password>`
- [x] `notifySpecialOffer <message, mealName, specialPrice>` --> deprecade see code
- [ ] `showMeals <orderingCriteria>` --> wait for new req.