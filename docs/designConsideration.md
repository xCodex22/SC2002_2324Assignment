# Design Consideration
As one can tell from the repository, the code base is quite significant. We will only go through some of the key considerations. We recommend to look through the actual code base and *run* the program on your own

## Object Oriented Concepts

### Encapsulation

One will find many instances of **encapsulation** in this project.  

### Inheritance

The main use case of inheritance in the project is in defining of `User` classes. In the hospital management system, the range of roles are `Patient`, `Doctor`, `Pharmacist` and `Adminsitrator`. These would share common **overlapping attributes**, such as `BasicInfo`, which are the class defining the member fields for primary, non-medical personal information such as `firstName`, `lastName`, `phoneNumber`, `gender` and so on.

Therefore, `User` is defined as the **base class** which **has a** `BasicInfo` class. `Patient`, `Doctor`, `Pharmacist` and `Administrator` are **derived classes** which **extends** from `User`

### Polymorphism

In the **Menu** class, which contains various **methods** to call out user interfaces for I/O handing, the **base class** `User` is often the parameter in deciding the execution of the **methods**. **Runtime Polymorphism** is important because we *do not* know which user will be logged in to the system, which affects the privileges and actions they have over the system. This is extremely relevant to the project because `User` determines the access. 

To illustrate, suppose we want to display patient's `MedicalInfo`, which are sensitive medical records. We would want the doctor to view *everything*, including the hospital ID and **memo**. However, if the current `User` is `Patient`, then it is not a good idea for them to see all these information, both for security and clarity. The following is example of how we used **polymorphism** to handle this:

```
          if (user instanceof Patient) {
            Patient patient = (Patient) user;
            patient.getBasicInfo().displayInfo();
            patient.getMedicalInfo().displayInfo(user);
          }
```

Because of the way `Java` handles dynamic binding, we often use the keyword `instanceof` to downcast the `User` class. Note that **method overloading** is not used because it *does not* actually avoid repetitions of codes, and the function signatures are the **same**.

### Abstraction

## Object Oriented Principle

## Problem-specific Design Choices

### Added Features (!!)

There are many added features and attempts to improve user experience in this CLI. Again, the best way to experience this is by running the program.

**Database Related**
- **Fully featured local save ability**: Every changes made by `User` in the session is **saved locally**. This means that logging out of the CLI will save every changes, including registration, change of password, appointment booking etc. This also means that **account creation** will also **initialise all necessary files locally**. This is the core philosophy that our CLI has, because we believe that there **no use case** of a hospital management system if it doesn't save the information. In real world, the database is often stored in a server, however due to the constraint and scope of the project, we used local directories as our data bases.
- **Patient registration:** It does not make sense for `Administrator` or other privileged `User` to manually add new patients 

**Interface Related**
- **Application like experience**: Running the program, one will realise that the CLI tries to mimic the experience of an actual application, where each menu and functions opens a new "window" and exiting the session returns to the previous "window". This is done by clearing the I/O of the terminal 
- **Hiding passwords when logging in**: This is a common sane default, any logging system should hide the password to ensure security
- 

**Functional Related**
- **Sanitiser for input handling**: We have an entire **singleton** dedicated to sanitise user inputs. This is done by declaring the relevant `regex` and throwing `Exceptions` if it is not matched. The philosophy is that, we do not want to be cleaning the entries in the databases subsequently, as it can lead to many challenges in reading of the data if the entries are inconsistent (since we are fully supporting **local save**). This class also handles many of the I/O `Exceptions`. To illustrate, the following is an example on how the `Sanitise` class read the `dateOfBirth` of `User`
- **ASCII art display of doctor's personal schedule as a calendar:**
- 

### Design Challenges

The biggest challenge in the project is **time**. The project requirement is very open-ended about how one can implement the various features. While we try to give our best effort in making every feature **clean**, **safe** and **sane**, we really do not have sufficient time to do this. This is also because we are supporting local save feature, so the representation of data as **directories** and **files** need to be well thought out to ensure efficient computation. 

For example, how would you represent the `Doctor` personal schedule? 
- The schedule should give the `Doctor` an overview of what's going on in the month, year and day
- A real world schedule also maps the tasks to calendar dates, which means it fully supports calculation of the weekday of the day, the number of days in the month and the calculation of the presence of leap year
- This is trivial if we can use other's API, but we will only use built-in libraries and local files 
- Additionally, each day has **different time slots**, assuming there are 7, using naive approach with a single `.csv` would mean that we will have a $7 \times 365$ matrix, which is 2,555 entries **just for a single year**

We have achieved, although only for the year of 2024, with a deliberate directory structure for local saves as mentioned earlier. 

This is just one of the issues in which an open ended project requirement without exact test cases lead to. 

**Theory v.s. Practice**

A jarring issue that one can easily see if the `Menu` class, which is simply many class methods combined together. The *correct* way to implement such classes would be to first define `interface`  


### Key Assumptions

## Coupling and Cohesion of Classes

## Reflection 

The most important learning point is that, the team could have chose a **unit testing framework**, such as the`JUnit`. This would have saved a lot of time by automating `assetions` and test cases. Because not all members are familiar with unit testing, we have to manually re-compile and test individual edge cases, which costed us a lot of time. In fact, industrial practices dictate the use of unit test because merging untested or under-tested branches into production branches could lead to catastrophic result. In our case, it is many wasted time, but for a company, the entire service may have to be suspended temporarily for a emergency patch to be applied.
