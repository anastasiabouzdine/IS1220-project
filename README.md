# Software design (IS1220) Final Project
The goal of this project is to develop a software solution, called *MyFoodora*,
whose functionality are similar to that of nowadays food delivery systems.

The project consists of two parts, the **core** and the **interface**.

## To do
- [x] make `treatNewOrders` work for multiple orders
- [x] number of `preparedOrders` for a `Restaurant` ? 
- [x] handling of the `dateAfter` as the current date ?
- [x] update users lists (.txt)
- [x] review `toString()` functions
- [x] finish JavaDoc
- [x] verify disactivated users don't get involved in `Core` functions
- [x] rounding problems in Coretest --> because of bad dates handling (use of compareTo instead of before and after)
- [x] problem with `MarkupProfit` Class

#### Managers
- [x] add/remove users
- [x] activate/disactivate users
- [x] profit related attribute
- [x] income over period
- [x] income per `Customer`
- [x] target profit policy
- [x] most selling `Restaurant`
- [x] most active `Courier`
- [x] setting delivery policy

#### Restaurants
- [x] edit the menu (add/remove `Dish`)
- [x] add/remove `Meal`
- [x] add/remove meal of the week special offer
- [x] set generic and special `discountFactors`
- [x] sort of shipped orders according policy

#### Customers
- [x] place orders --> in GUI
- [x] choose fidelity plan option --> in `Customer` class
- [x] access account info --> in GUI
- [x] get history of orders and fidelity points
- [x] give/rm consensus for notification --> in GUI

#### Couriers
- [x] register/unregister their account
- [x] set their state
- [x] change their position
- [x] accept/refuse to a delivery call

#### Core system
- [x] setting of the 3 profit related values
- [x] allocation of a courier to an order placed by a customer
- [x] notify users of special offers
- [x] compute total income
- [x] choose target policy
