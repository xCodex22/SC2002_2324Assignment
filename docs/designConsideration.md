# Design Consideration
As one can tell from the repository, the code base is quite significant. We will only go through some of the key considerations. We recommend to look through the actual code base and *run* the program on your own

## Object Oriented Concepts

### Encapsulation



### Inheritance

The main use case of inheritance in the project is in defining of `User` classes. In the hospital management system, the range of roles are `Patient`, `Doctor`, `Pharmacist` and `Adminsitrator`. These would share common **overlapping attributes**, such as `BasicInfo`, which are the class defining the member fields for primary, non-medical personal information such as `firstName`, `lastName`, `phoneNumber`, `gender` and so on.

Therefore, `User` is defined as the **base class** which **has a** `BasicInfo` class. `Patient`, `Doctor`, `Pharmacist` and `Administrator` are **derived classes** which **extends** from `User`

### Polymorphism



### Abstraction

## Object Oriented Principle

## Problem-specific Design Choices


## Coupling and Cohesion of Classes
