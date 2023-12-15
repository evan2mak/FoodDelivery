# C323 Final Project - Food Delivery App: Evan Tomak

This project is an app that allows a user to create an account, place an order from a list of restaurants, see restaurant details, select items to order, checkout, modify orders, see the total price of an order, track an order, look at order history, and look at calendar view information.

The functionality is described in more detail below:

## User Screen

[X] The user screen is where the user will have the ability to sign in, sign up, or sign out of the app.

[X] If the user does not have an account, they must sign up with their email and password.

[X] If the user does not have an account and they try to sign in or sign out, an error message will be thrown.

[X] The user also must input both the email and password. If either is empty, it will throw an error message when trying to sign up or sign in.

[X] If the user has an account, they must click sign in. If they click sign up, an error message will be thrown.

[X] Upon successful sign in or sign up, the user will be navigated to the notes list fragment.


## Home Screen

[X] The home screen shows the 5 most recent restaurants at the top. To see all 5, must scroll horizontally.

[X] The 8 total restaurants are at the bottom. To see all 8, scroll vertically.

[X] There is a menu navigation drawer in the top left. This slides out a navigation drawer.

[X] The Home tab just closes the drawer.

[X] The Recent Orders tab shows you all of the orders that have been placed on your account.

[X] This includes order details like restaurant name, food item information, total price, delivery address (if given), special comments (if given), and the time and date the order was placed.

[X] The calendar view shows a calendar -- when you select a date, it shows the total amount of money you spent on that date on restaurants in the app.

[X] The sign out button navigates the user back to the user screen, signing them out.

[X] When a user clicks on a restaurant title, they navigate to the Restaurant screen.


## Restaurant Screen

[X] The restaurant screen shows 3 images of the restaurant at the top. These can be scrolled horizontally.

[X] There are 5 food items to choose from with different prices. You can adjust the quantity as well.

[X] After you are finished, you can select checkout to move to the checkout screen.


## Checkout Screen

[X] The checkout screen is where the user checks out.

[X] The items that they selected on the page before display here. You can adjust the quantities here too.

[X] You can also add a delivery address and/or special instructions.

[X] You can also go back to the restaurant screen using the modify button to modify your order.

[X] Lastly, you can place the order.

[X] When you place the order, it is saved in the Recent Orders tab on the Home screen. Also, a running clock starts, and a notification will be sent to you shortly when your food is delivered.

[X] You now move onto the Order screen.


## Order Screen

[X] The order screen shows your placed order. It shows the restaurant name, order details like food name and quantity, the total price, delivery address and special instructions, and your order time.

[X] You can then click the Home button to go back to the home screen, or you can click the Track Order button which displays a map of your order route.

## 

The following functions/extensions are implemented:


