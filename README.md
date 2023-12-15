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

## AllRestaurantsAdapter

Manages the RecyclerView adapter for displaying a list of restaurants.

OnItemClickListener: Interface to handle item click events in the RecyclerView.

ViewHolder: Holds the views for each item in the RecyclerView.

onCreateViewHolder: Creates a new ViewHolder instance for each item in the RecyclerView.

onBindViewHolder: Binds data to the ViewHolder for a specific item in the RecyclerView.

getItemCount: Returns the total number of items in the RecyclerView.

## AllRestaurantsFragment

Fragment responsible for displaying a list of all restaurants.

## CalendarViewFragment

Fragment responsible for displaying a calendar view and calculating total spent per day.

onCreateView: Inflates the fragment's layout and sets up the calendar view and button click listener.

calculateTotalSpent: Calculates the total amount spent by the user on a selected date range.

showTotalSpent: Displays the total amount spent as a toast message.

onDestroyView: Removes the ValueEventListener to prevent memory leaks when the fragment is destroyed.

## CheckoutFoodItemAdapter

Manages the RecyclerView adapter for displaying food items in the checkout screen.

onCreateViewHolder: Creates a new ViewHolder instance for each item in the RecyclerView.

onBindViewHolder: Binds data to the ViewHolder for a specific item in the RecyclerView.

getItemCount: Returns the total number of food items in the RecyclerView.

## CheckoutFragment

Fragment responsible for managing the checkout process.

calculateTotalAmount: Calculates the total amount for the food items in the order.

startDeliveryTimer: Initiates the delivery timer and service.

createDeliveryNotification: Creates a delivery notification.

## DeliveryService

A background service for simulating food delivery with a timer.

## FoodItemAdapter

Manages the RecyclerView adapter for displaying food items.

onCreateViewHolder: Creates a new ViewHolder instance for each item in the RecyclerView.

onBindViewHolder: Binds data to the ViewHolder for a specific item in the RecyclerView.

getItemCount: Returns the total number of food items in the RecyclerView.

## HomeFragment

Represents the home screen of the application.

onCreateView: 

Initialize RecentRestaurantsFragment and replace the corresponding container.
Initialize AllRestaurantsFragment and replace the corresponding container.
Set navigation item selection listener.

## MainActivity 

The primary activity that manages navigation and Firebase authentication.

onCreate: Initializes components and sets up navigation based on authentication status.

## Order

Order data class.

## OrderItem

Order item data class.

## OrdersAdapter

Manages the RecyclerView adapter for displaying orders.

setOrders: Update the list of orders and notify the adapter of data changes.

onCreateViewHolder: Create a new OrderViewHolder instance for each item in the RecyclerView.

onBindViewHolder: Bind data to the OrderViewHolder for a specific order in the RecyclerView.

getItemCount: Return the total number of orders in the RecyclerView.

getOrderDetails: Get a formatted string representing order details.

formatOrderTime: Format the order time as a string.

## OrderScreenFragment

Displays order details and allows tracking of orders.

getOrderDetails: Get a formatted string representing order details.

formatOrderTime: Format the order time as a string.

showTrackOrderDialog: Display a dialog for tracking the order.

## OrderViewModel

Manages order-related data for the application.

## RecentOrdersFragment

Fragment responsible for displaying the user's recent orders.

loadOrders(): Query the database to retrieve the user's orders

## RecentRestaurantsAdapter

Adapter for displaying recent restaurant items in a RecyclerView.

## RecentRestaurantsFragment

Fragment for displaying recent restaurant items and navigating to restaurant details.

## Restaurant

Restaurant data class.

## RestaurantFragment

Fragment for displaying restaurant details and food items.

loadFoodItems(): Function to load food items from Firebase.

getRestaurantImages(): Function to retrieve restaurant images based on restaurant ID.

## RestaurantImagesAdapter:

Adapter for displaying restaurant images in a RecyclerView.

## User

Data class for users.

## UserRepository

Manages user authentication and registration using Firebase.

init:

Initialize the user LiveData based on the current Firebase authenticated user.

login: 

Authenticates a user using email and password.

register: 

Registers a new user using email and password.

saveUserToDatabase: 

Saves the authenticated user's details to Firebase Database.

logout: 

Logs out the authenticated user.

## UserRepositorySingleton

Provides a singleton instance of UserRepository.

## UserScreen

Fragment for user authentication including sign in, sign up, and sign out.

onCreateView: 

Inflates the layout and initializes UI components.

onViewCreated: 

Sets up UI behavior and event listeners.

onDestroyView: 

Removes observers when the view is destroyed.

onSignInClicked: 

Handles sign in button click.

onSignUpClicked: 

Handles sign up button click.

onSignOutClicked: 

Handles sign out button click.

## UserViewModel

ViewModel for managing user authentication and registration.

login: 

Attempts to log in the user with the provided email and password.

register: 

Attempts to register a new user with the provided email and password.

logout: 

Logs out the current authenticated user.

## UserViewModelFactory

Factory class for creating instances of UserViewModel.

## Notes

This was a challenge! I had a lot of trouble, so the whole thing was hard. The really hard part was tracking and loading images for each restaurant. The calendar view took a lot of work as well. This one was tough, but interesting. Had a great semester! Thank you.


# Video Walkthroughs

Here's a walkthrough of implemented user stories:

This clip shows email/password validation edge cases.

![studio64_AH3GhLszgZ](https://github.com/evan2mak/Notes2/assets/128643914/bfb24855-dd2c-48cc-bdc7-78030e977dfe)

This clip shows the home screen. It shows the navigation drawer with all of its components and also shows how to scroll for recent restaurants.

![studio64_khWjrSPOgB](https://github.com/evan2mak/FoodDelivery/assets/128643914/7ca6d09b-07c8-4637-9533-ad3d4b109f67)

Here is the ordering process. Should cover all edge cases. 

![studio64_xWBIduWRJA](https://github.com/evan2mak/FoodDelivery/assets/128643914/1fa475f5-e3d6-4f63-8e83-c5bedda6e36a)



## License

    Copyright 2023 Evan Tomak.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express implied.

    See the License for the specific language governing permissions and
    limitations under the License.

