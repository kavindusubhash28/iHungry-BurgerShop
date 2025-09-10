# 🍔 iHungry Burger Shop – Assignment 1

This is my **Assignment 1** project for the iHungry Burger Shop application.  
It is a **Java console-based program** that focuses on building the **core foundation** of the Burger Shop Management System.

## 📌 Features (Assignment 1 Scope)
- **Main Menu** with 7 options (only *Place Order* + *Exit* implemented).
- **Place Order** functionality:
  - Auto-generate Order IDs (`B001`, `B002`, ...).
  - Validate Customer ID (must start with `0` and be 10 digits).
  - If Customer ID already exists → reuse stored customer name.
  - If new Customer ID → prompt to enter name.
  - Enter Burger Quantity (validated >0).
  - Order automatically assigned **PREPARING** status.
  - Display **bill amount** (`quantity × 500`).
  - Confirm before saving order.
  - Option to place another order in a loop.
- **Exit option** to close the program.

## 🛠️ Technologies
- Java
- Console-based (CLI)
- Arrays for storing data

## 🎯 Learning Outcomes
- Implementing **arrays** to manage multiple data fields (IDs, names, quantities, status).
- Validating **user input** in Java.
- Creating a **menu-driven program** with loops and methods.

