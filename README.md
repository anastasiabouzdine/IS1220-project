# Software design (IS1220) Final Project
The goal of this project is to develop a software solution, called *MyFoodora*,
whose functionality are similar to that of nowadays food delivery systems.

The project consists of two parts, the **core** and the **interface**.

## To do
- [ ] decide if functions should be restricted to users in the `Core` or in the `GUI`
- [ ] eventually divide CoreTest file into multiple more specific test files
- [x] make `treatNewOrders` work for multiple orders
- [x] number of `preparedOrders` for a `Restaurant` ? 
- [ ] handling of the `dateAfter` as the current date ?
- [ ] name of `priceInter` unappropriate

#### Managers
- [x] add/remove users
- [x] activate/disactivate users
- [x] profit related attribute
- [x] income over period
- [x] income per `Customer`
- [ ] target profit policy
- [x] most selling `Restaurant`
- [x] most active `Courier`
- [x] setting delivery policy

#### Restaurants
- [ ] edit the menu (add/remove `Dish`)
- [ ] add/remove `Meal`
- [ ] add/remove meal of the week special offer
- [ ] set generic and special `discountFactors`
- [ ] sort of shipped orders according policy

#### Customers
- [ ] place orders
- [ ] choose fidelity plan option
- [ ] access account info
- [ ] give/rm consensus for notification

#### Couriers
- [ ] register/unregister their account 
– [ ] set their state
– [ ] change their position
– [x] accept/refuse to a delivery call

#### Core system
- [x] setting of the 3 profit related values
- [x] allocation of a courier to an order placed by a customer
- [ ] notify users of special offers
- [x] compute total income
- [ ] choose target policy
