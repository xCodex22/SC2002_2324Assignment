# The Report 

As one can tell from the repository, the code base is quite significant. We will only go through some of the key considerations. We recommend to look through the actual code base and *run* the program on your own

## UML Class Diagram

Refer to [`UML.png`](UML.png)

## Object Oriented Concepts

### Encapsulation

One will find many instances of **encapsulation** in this project. Attributes within `Info` type classes, such as `BasicInfo`, `MedicalInfo` and `ScheduleIno` are all private member fields. Specific functionality only relevant to classes are also encapsulated in the class itself. For example, `update()` in `BasicInfo` only updates the information of the `BasicInfo` class to the database.  

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

- **Data Abstraction**: Private member fields about user information is not accessible to other classes. Other classes do not need to know how these member fields are obtained. For example, to get the email address of the current user:
```
    String email = user.getBasicInfo().getEmailAddress();
```
- **Process Abstraction**: Other classes do not need to know the specific implementation of on how the process works,  but only know to how to call the methods. For example, to register an account, the process needs to read and write to files, and calculate the unique hospital ID to be returned. However, to call it, it is simply  
```
    account.register(info, inputRole)
```

### Cohesion 

Our classes are encapsulated with specific responsibility. For example, a `Doctor` class has `BasicInfo`, `ScheduleInfo`. `BasicInfo` class is responsible for updating and returning any changes in personal information, whereas `ScheduleInfo` handle tasks related to setting and returning availability. 

### Coupling

Extending from the previous example, these classes have no dependency between each other, hence modifying one class would not lead to compilation or runtime erros, effectively achieving **low coupling**. For example, in `MedicalInfo` of `Patient` class, modifying the class so that we handle extra attributes or functionality would not affect information handling in `BasicInfo`, which deals with personal information.


## Object Oriented Principle

The HMS was designed with Object-Oriented Programming principles to promote
reusability. This approach enhances code maintainability, making it well-suited for future
expansion and updates. We implemented this by applying SOLID design principles, as
covered in the module

### S - Single-responsibility Principle

*"A module should be responsible and to one, and only one actor"*. 

- `AppointmentSystem` deals solely with anything related to booking, updating, checking of appointment
- `AccountSystem` deals solely with logging in, registration, changing password and other **account** specific functionalities
- `StaffManagementSystem` deals soley with handling data of staff members, which are `Dcotor` and `Pharmacist`. Wiewing, editing, adding and finding of staffs, etc are all handled by this class.

### O - Open-closed Principle

*"modules should be open for extension, but closed for modification"*. In each of the `System` classes, adding new functionalities will not lead to modification or re-write of the code, because there is no relevant dependencies.

### L - Liskov Substitution Principle

The base classes `User` can be easily replaced with derived classes `Doctor`, `Patient` etc, without breaking the application. This is shown previously in how we handle polypmorphism.

### I - Interface Segregation Principle

The classes are not forced to depend on `interface` or `abstract` classes even if they are not required to use. 

By avoiding pointless methods on implementations of
classes, this approach seeks to improve maintainability, flexibility, and organisation of the
code. 

This is done by making `interface` more specific. For example, `InventorySystem` implements `IDispense`, `IAddStock` and `IRemoveStock` etc at the same time, only because it needs it. This segregation allows future `System` classes to pick only the functionality they desire.

### D - Dependency Inversion Principle

his principle illustrates that high-level modules should depend on abstractions rather than on
concrete implementations. In the context of the `User` class, this principle has been applied
by ensuring that the class interacts with abstractions (interfaces or abstract classes) instead of
relying directly on specific implementations of its dependencies. This approach decouples the
`User` class from lower-level modules, making it more flexible and easier to extend or
modify. By depending on abstractions, the `User` class can incorporate new functionalities
without requiring changes to its core structure, thus enhancing the maintainability and
scalability of the codebase.

## Problem-specific Design Choices

### Added Features (!!)

There are many added features and attempts to improve user experience in this CLI. Again, the best way to experience this is by running the program.

**Database Related**
- **Fully featured local save ability**: Every changes made by `User` in the session is **saved locally**. This means that logging out of the CLI will save every changes, including registration, change of password, appointment booking etc. This also means that **account creation** will also **initialise all necessary files locally**. - **Patient registration:** It does not make sense for `Administrator` or other privileged `User` to manually add new patients, we allow the creation of new `Patient` via the starting menu, and initialise all relevant files
- **File writing and reading to temporary files**: To prevent unexpected behaviours when reading and writing to the same file on difference instances of the CLI, we first write any changes to a temporary `.csv~`, before overwritting the orginal `.csv` and then deleting the `.csv`

**Interface Related**
- **Application like experience**: Running the program, one will realise that the CLI tries to mimic the experience of an actual application, where each menu and functions opens a new "window" and exiting the session returns to the previous "window". This is done by clearing the I/O of the terminal 
- **Hiding passwords when logging in**: This is a common sane default, any logging system should hide the password to ensure security
- **Fool-proof editing of personal information:** When users update their personal information (`BasicInfo`), they may accidentally quit the interface page *without* saving. We implemented a safety mechanism to inform users of chnages made that were not saved, and give option to either abort or save. An example of the output is given below:
```
=========[ !! WARNING: You have Unsaved Changes ]==============
===========[ Personal Information ]============
[-] ID Number: 12345
[-] Account Type: PATIENT
[1] First Name: Joe
[2] Last Name: Biden
[3] Gender: MALE
[4] Date of Birth: 12-03-1989
[5] Phone Number: 88881234
[6] Email Address: readldonald@gmail.com

1. Confirm
2. Cancel
Enter option (1-2): 
```

**Functional Related**
- **Sanitiser for input handling**: We have an entire **singleton** dedicated to sanitise user inputs. This is done by declaring the relevant `regex` and throwing `Exceptions` if it is not matched. The philosophy is that, we do not want to be cleaning the entries in the databases subsequently, as it can lead to many challenges in reading of the data if the entries are inconsistent (since we are fully supporting **local save**). This class also handles many of the I/O `Exceptions`. To illustrate, the following is an example on how the `Sanitise` class read the `emailAddress` of `User`

```
  /**
   * Santise user input email address by validating format; does not check if email is real
   * @return the santised email address
   */
  public static String readEmailAddress() throws Exception {
    String res = null;
    try {
      Scanner sc = new Scanner(System.in);
      res = sc.nextLine().trim().replaceAll("\\s+","");
      if (!VALID_EMAIL_ADDRESS.matcher(res).matches()) throw new Exception("[-] Invalid Email Address. Try Again.");
    } catch(InputMismatchException e) {
      throw new Exception("[-] Illegal Characters. Try Again.");
    }
    return res.toLowerCase();
  }

  public static final Pattern VALID_EMAIL_ADDRESS = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
```

- **ASCII art display of doctor's personal schedule as a calendar:** To replicate how a real world schedule should behave, we display the dates as **calendar dates** with relevant labels, giving `Doctor` an actual overview of their schedule. The week dates are directly mapped via a 1D array with the starting day of the month. Algorithms exist to calculate for any date, the starting weekday, but for simplicity, we used direct mapping since we are only concerned with the year 2024. An example of the output is given below
```
=====[ January ]======
Su Mo Tu We Th Fr Sa
---------------------
   01 02 03 04 05 06 
   !! OO !! OO OO XX 
---------------------
07 08 09 10 11 12 13 
XX XX XX XX XX XX XX 
---------------------
14 15 16 17 18 19 20 
XX XX XX XX XX XX XX 
---------------------
21 22 23 24 25 26 27 
XX XX XX XX XX XX XX 
---------------------
28 29 30 31 
XX XX XX XX 
---------------------

Legends: 
[XX] Date marked unavailable
[OO] Date marked available
[!!] Date with pending appointment request

[1] Next
[2] Previous
[3] Exit
Enter option (1-3): 
```

### Design Challenges

The biggest challenge in the project is **time**. The project requirement is very open-ended about how one can implement the various features. While we try to give our best effort in making every feature **clean**, **safe** and **sane**, we really do not have sufficient time to do this. This is also because we are supporting local save feature, so the representation of data as **directories** and **files** need to be well thought out to ensure efficient computation. In other words, the **data structure** in place is crucial in ensuring clean and effective code.

For example, how would you represent the `Doctor` personal schedule? 
- The schedule should give the `Doctor` an overview of what's going on in the month, year and day
- A real world schedule also maps the tasks to calendar dates, which means it fully supports calculation of the weekday of the day, the number of days in the month and the calculation of the presence of leap year
- This is trivial if we can use other's API, but we will only use built-in libraries and local files 
- Additionally, each day has **different time slots**, assuming there are 7, using naive approach with a single `.csv` would mean that we will have a $7 \times 365$ matrix, which is 2,555 entries **just for a single year**

We have achieved, although only for the year of 2024, with a deliberate directory structure for local saves. The following is an example of the database structure in place:
```console
$ ls -R 12346/

12346/2024:
01  02	03  04	05  06	07  08	09  10	11  12

12346/2024/01:
01.csv	03.csv	05.csv	07.csv	09.csv	11.csv	13.csv	15.csv	17.csv	19.csv	21.csv	23.csv	25.csv	27.csv	29.csv	31.csv
02.csv	04.csv	06.csv	08.csv	10.csv	12.csv	14.csv	16.csv	18.csv	20.csv	22.csv	24.csv	26.csv	28.csv	30.csv

12346/2024/02:
...
```

Instead of reading every time slot one by one in the year, this directory structure allows us to index the date directlyand read only the relevant time slot. If the date is invalid, then it would throw a `FileNotFoundException`.

### Key Assumptions

- **Compatibility**: For the CLI to render properly, users must use a **true terminal**, not one that is built-in within a IDE. Additionally, depending on the fonts you have chosen for your terminal, some of the alignment of texts may seem off. We recommend to use **monospace** terminal fonts
- **Termination Signal**: If a user forces a termination unexpectedly, for example, through `keyboardInterrupt`, the data base may become corrupted and the CLI will exhibit weird behaviour. We assume that the users will let our `ExceptionHandler` do their job
- **The data files MUST NOT be tampered with**: Because of how we read and write files in the local data bases, if one is to modify the `.csv`, either accidentally or intentionally, most of the things *will break*. For example, 
```
    String[] line = scanner.nextLine().split(",");
```
will behave unexpectedly if one is to remove the commas delimiting the columns in the data file
- **Date related to scheduling and appointment booking only supports for the year 2024:** As highlighted previous, to make the problem simpler while retaining the core aspects, we only work a single year. However, the design can be extended to subsequent years if need be
- **Tracking of current date**: Although we can track the current date and time of the user session via `Calendar`, we do away with it because this would also imply a dynamic calendar, that is, depending on the date, users will see the range of dates based on the current offset, assuming that they are allowed to book appointment one year in advance, this means that `User` can select any arbitrary date within the year 2024
- **Hospital ID**: The hospital IDs must be **unique**. To ensure this, we take the latest ID number and increase it by 1. This would also mean that we can only support `int` sized ID number. Because account registration into the data base has the accounts, and hence ID numbers stored **monotonically increasing**, this method works
- **Searching for user**: From the above assumption that will hold unless a deliberate tempering of the data files take place, we implement searching via **binary search**. Therefore, it has dependency on the previous
- **Valid Phone Number**: The `regex` in place checks for valid **Singapore-based** phone numbers, that is, it checks whether the input format matches the local phone number
- **Valid Email Address**: The `regex` in place ensures alpha-numerals to precede before `@` character, followed by a common domain name. It does not check for validity of email, nor does it handle special, shortened emails
- **Other input handling issues**: We do not check whether the phone number etc are actually real and valid, as this would require the use of other API and we believe that it should be out of the scope of this project


## Test Cases

Test Case 1:
```
===========[ Personal Information ]============
[-] ID Number: 12345
[-] Account Type: PATIENT
[1] First Name: Joever
[2] Last Name: Biden
[3] Gender: MALE
[4] Date of Birth: 12-03-1989
[5] Phone Number: 88881234
[6] Email Address: readldonald@gmail.com
===========[ Medical Information ]============
[-] Blood Type: B-
=============[ Medical Records ]==============
[-] Service date: 12-03-2020
[-] Doctor assigned: Dr. Stephen Strange
[-] Service administered: Consultation
[-] Diagnosis result: headache
[-] Medication administered: Paracetamol 10 TAB
[-] Treatment plan administered: NIL
----------------------------------------------
[-] Service date: 12-05-2020
[-] Doctor assigned: Dr. Shane Dawson
[-] Service administered: Blood Test
[-] Diagnosis result: NIL
[-] Medication administered: NIL NIL
[-] Treatment plan administered: NIL
----------------------------------------------

[!] Enter "1" to continue:    

```

Test Case 2:
```
========= Your Information has been Updated ==============
===========[ Personal Information ]============
[-] ID Number: 12345
[-] Account Type: PATIENT
[1] First Name: Joe
[2] Last Name: Biden
[3] Gender: MALE
[4] Date of Birth: 12-03-1989
[5] Phone Number: 88881234
[6] Email Address: readldonald@gmail.com
Enter "1" to quit: 
```

Test Case 3:
```
=======[ View Available Slots ]=======

Enter date in DD-MM format: 
Enter here: 01-01

[!] The available slots for 01-01 are: 
0900-1000: Dr. Shane Dawson ID: 12357
1100-1200: Dr. Shane Dawson ID: 12357
0900-1000: Dr. Stephen Strange ID: 12346
1000-1100: Dr. Stephen Strange ID: 12346
1100-1200: Dr. Stephen Strange ID: 12346
1400-1500: Dr. Stephen Strange ID: 12346

[!] Enter "1" to continue: 

```

Test Case 4:
```
======[ Schedule Appoinment ]=====

Enter date in DD-MM format: 
Enter here: 01-01
Select a time slot: 
[1] 0900-1000
[2] 1000-1100
[3] 1100-1200
[4] 1300-1400
[5] 1400-1500
[6] 1500-1600
[7] 1600-1700
Enter option (1-7): 6
Enter ID of doctor to schedule with: 12346
[+] Successfully scheduled appointment

[!] Enter "1" to continue:
```

Test Case 5:

```
=====[ Reschedule an appointment ]=====
First, enter the original schedule date

[!] Enter "1" to continue: 1

Enter date in DD-MM format: 
Enter here: 01-01
Select a time slot: 
[1] 0900-1000
[2] 1000-1100
[3] 1100-1200
[4] 1300-1400
[5] 1400-1500
[6] 1500-1600
[7] 1600-1700
Enter option (1-7): 7
Enter the ID of the doctor that you have scheduled with: 12346

Next, enter new date to reschedule

Enter date in DD-MM format: 
Enter here: 02-01
Select a time slot: 
[1] 0900-1000
[2] 1000-1100
[3] 1100-1200
[4] 1300-1400
[5] 1400-1500
[6] 1500-1600
[7] 1600-1700
Enter option (1-7): 1
Enter the ID of the doctor that you want to schedule with: 12357
[+] Successfully rescheduled

[!] Enter "1" to continue: 1

======[ All Scheduled Appointments ]=======

[1] Scheduled Date [2] Scheduled Slot [3] Appointment Status [4] Doctor Name [5] Doctor ID
01-01-2024 1500-1600 cancelled Stephen Strange 12346 
01-01-2024 1300-1400 confirmed Stephen Strange 12346 
01-01-2024 0900-1000 cancelled Stephen Strange 12346 
02-01-2024 0900-1000 pending Shane Dawson 12357 
01-01-2024 1600-1700 cancelled Stephen Strange 12346 

[!] Enter "1" to continue:

```

Test Case 6:

```
====[ Cancel an appointment ]====

Enter date in DD-MM format: 
Enter here: 01-01
Select a time slot: 
[1] 0900-1000
[2] 1000-1100
[3] 1100-1200
[4] 1300-1400
[5] 1400-1500
[6] 1500-1600
[7] 1600-1700
Enter option (1-7): 6
Enter the ID of the doctor that you have scheduled with: 12346
[+] Successfully cancelled appointment

[!] Enter "1" to continue: 

```
Test Case 7:

```
======[ All Scheduled Appointments ]=======

[1] Scheduled Date [2] Scheduled Slot [3] Appointment Status [4] Doctor Name [5] Doctor ID
01-01-2024 1500-1600 cancelled Stephen Strange 12346 
01-01-2024 1300-1400 pending Stephen Strange 12346 
01-01-2024 0900-1000 cancelled Stephen Strange 12346 
01-01-2024 1600-1700 pending Stephen Strange 12346 

[!] Enter "1" to continue: 
```

Test Case 8:

```
====[ Past Appointment Outcome Records ]=====

[!] Colums: Patient ID, Date, Slot, Service, Doctor ID, Diagnosis, Medication, Qty, Status, Treatment, Memo

12345,12-12-2023,0900-1000,Physical Examination,12346,headache,Paracetamol,10,dispensed,NIL,Patient is stressed
12345,01-01-2024,0900-1000,System Test,12346,NIL,Paracetamol,10,pending,NIL,System Test

[!] Enter "1" to continue: 

```

Test Case 9:
```
======[ View Patient Medical Record ]======
Enter patient's ID number: 12345
===========[ Medical Information ]============
[-] Blood Type: B-
=============[ Medical Records ]==============
[-] Service date: 12-03-2020
[-] Service administered: Consultation
[-] Doctor assigned: Dr. Stephen Strange
[-] Doctor ID: 12346
[-] Diagnosis result: headache
[-] Medication administered: Paracetamol 10 TAB
[-] Treatment plan administered: NIL
[-] Memo: NIL
----------------------------------------------
[-] Service date: 12-05-2020
[-] Service administered: Blood Test
[-] Doctor assigned: Dr. Shane Dawson
[-] Doctor ID: 12357
[-] Diagnosis result: NIL
[-] Medication administered: NIL NIL
[-] Treatment plan administered: NIL
[-] Memo: change blood type
----------------------------------------------

[!] Enter "1" to continue:
```

Test Case 10:
```
======[ Update Patient Medical Record ]======
Enter patient's ID number: 12345

[!] You are attempting to update the medical record of the following user:

===========[ Personal Information ]============
[-] ID Number: 12345
[-] Account Type: PATIENT
[1] First Name: Joe
[2] Last Name: Biden
[3] Gender: MALE
[4] Date of Birth: 12-03-1989
[5] Phone Number: 88881234
[6] Email Address: readldonald@gmail.com

Enter the date of update: 

Enter date in DD-MM format: 
Enter here: 04-01
Enter new diagnosis: 
diabetes
Enter new treatment plan: 
dialysis
[+] Updated patient's medical record
```

Test Case 11:
```
======[ January ]======
Su Mo Tu We Th Fr Sa
---------------------
   01 02 03 04 05 06 
   !! OO !! OO OO XX 
---------------------
07 08 09 10 11 12 13 
XX XX XX XX XX XX XX 
---------------------
14 15 16 17 18 19 20 
XX XX XX XX XX XX XX 
---------------------
21 22 23 24 25 26 27 
XX XX XX XX XX XX XX 
---------------------
28 29 30 31 
XX XX XX XX 
---------------------

Legends: 
[XX] Date marked unavailable
[OO] Date marked available
[!!] Date with pending appointment request

[1] Next
[2] Previous
[3] Exit
Enter option (1-3):
```

Test Case 12:
```
========[ Update Schedule ]========
Enter the date to modify: 
Enter date in DD-MM format: 
Enter here: 01-01

=====[ Schedule for 01-01 ]=====
[1] 0900-1000: Available
[2] 1000-1100: Available
[3] 1100-1200: Available
[4] 1300-1400: Pending Appointment by ID: 12345
[5] 1400-1500: Available
[6] 1500-1600: Pending Appointment by ID: 12345
[7] 1600-1700: Pending Appointment by ID: 12345


[!] Choose option:
[1] Mark a slot as available
[2] Mark a slot as unavailable
Enter option (1-2): 2

[!] Choose time slot base on the numbering given
Enter option (1-7): 1

=====[ Schedule for 01-01 ]=====
[1] 0900-1000: Unavailable
[2] 1000-1100: Available
[3] 1100-1200: Available
[4] 1300-1400: Pending Appointment by ID: 12345
[5] 1400-1500: Available
[6] 1500-1600: Pending Appointment by ID: 12345
[7] 1600-1700: Pending Appointment by ID: 12345

[+] Schedule updated successfully

[!] Enter "1" to continue:
```

Test Case 13:

```
=====[ Record Appointment Outcome ]=====
Enter patient ID: 12345
=======[ Accept or Decline Appointment Request ]=======
[1] Date [2] Time Slot [3] Patient ID
[ 1 ] 01-01-2024 1600-1700 12345
[ 2 ] 03-01-2024 1400-1500 12349

Enter the index of the scheduled appoitnment to accept or decline: 1
[!] Selected Request: 01-01-2024 1600-1700 12345

[1] Accept appointment request
[2] Decline appointment request
[!] Enter option (1-2): 2
[+] Appointment is declined.

[!] Enter "1" to continue: 
```

Test Case 14:

```
=======[ Your Upcoming Schedules ]======
[1] Date [2] Time Slot [3] Patient ID
01-01-2024 1300-1400 12345
01-01-2024 1500-1600 12345
01-01-2024 1600-1700 12345
03-01-2024 1400-1500 12349

[!] Enter "1" to continue:
```

Test Case 15:
```
=====[ Record Appointment Outcome ]=====
Enter patient ID: 12345
Enter date of appointment: 
Enter date in DD-MM format: 
Enter here: 01-01
Select a time slot: 
[1] 0900-1000
[2] 1000-1100
[3] 1100-1200
[4] 1300-1400
[5] 1400-1500
[6] 1500-1600
[7] 1600-1700
Enter option (1-7): 2
Enter name of service provided: test
Enter diagnosis: test

 Choose from the following medication to prescribe: 
[ 1 ] Paracetamol
[ 2 ] Ibuprofen
[ 3 ] Amoxicllin
Enter index of the medication: 2
Enter quantity to prescibe: 10
Enter treatment plan: test
Enter consultation notes: test

[!] Enter "1" to continue: 
```

Test Case 16:
```
=========[ View Appointment Outcomes ]===========

[!] Colums: Patient ID, Date, Slot, Service, Doctor ID, Diagnosis, Medication, Qty, Status, Treatment, Memo

[1] 12345,12-12-2023,0900-1000,Physical Examination,12346,headache,Paracetamol,10,dispensed,NIL,Patient is stressed
[2] 12345,01-01-2024,0900-1000,System Test,12346,NIL,Paracetamol,10,pending,NIL,System Test

[!] Enter "1" to continue: 
```

Test Case 17:
```
=========[ Update Prescription Status ]==========
[ 1 ] 12345,01-01-2024,0900-1000,System Test,12346,NIL,Paracetamol,10,pending,NIL,System Test
[!] Enter request to dispense based on index: 1
[+] Update Success
[!] Enter "1" to continue:
```

Test Case 18:
```
======[ View Medical Inventory ]========

Columns: Medicine Name, Unit, Max Stock, Current Stock, Low Stock Benchmark
Paracetamol TAB 100 70 20 
Ibuprofen TAB 50 40 10 
Amoxicllin CAP 100 5 10 

[!] Enter "1" to continue: 
```

Test Case 19:

```
===========[ Submit Replenishment Request ]=========
[!] The following medication have low stock count: 
Amoxicllin

[!] Choose the medication based on index

[ 1 ] Paracetamol
[ 2 ] Ibuprofen
[ 3 ] Amoxicllin
[!] Enter index: 3
[!] Enter amount of quantity to top up: 10
[+] Request sent

[!] Enter "1" to continue:
```

Test Case 20:
```
================[ Staff Management Menu ]==================
[1] View Current Staff
[2] Edit Staff Information
[3] Add Staff
[4] Remove Staff
[5] Exit this page

Enter option (1-5): 1

================[ Filter Options ]==================
[1] Filter by ID Number
[2] Filter by Role
[3] Filter by Gender
[4] Filter by Age
[5] Display All Staff
[6] Exit

Enter filter option (1-6): 3

Enter gender option: 
1. Male
2. Female
3. Others
Choose option (1-3): 2


==================[ Filter: Gender  ]======================


==================[ List of Doctors ]======================

===========[ Personal Information ]============
[-] ID Number: 12357
[-] Account Type: DOCTOR
[1] First Name: Shane
[2] Last Name: Dawson
[3] Gender: FEMALE
[4] Date of Birth: 12-11-1999
[5] Phone Number: 81010001
[6] Email Address: shane@gmail.com

==================[ List of Pharmacists ]======================

===========[ Personal Information ]============
[-] ID Number: 12358
[-] Account Type: PHARMACIST
[1] First Name: Ellen Jia Hui
[2] Last Name: Lee
[3] Gender: FEMALE
[4] Date of Birth: 10-10-1989
[5] Phone Number: 80000000
[6] Email Address: ellen@gmail.com

[!] Enter "1" to continue: 

```

Test Case 25: 
```
===[ Change Your Password ]===
1. Change Password
2. Exit
Enter option (1-2): 1
Enter your old password: 
Enter your new password: 
Confirm your new password: 
[-] Wrong Password
Enter option (1-2): 1
Enter your old password: 
Enter your new password: 
Confirm your new password: 
[+] Password has been changed successfully
Enter option (1-2): 

```
Test Case 26:
```
=================================================================
                   [!!] Wrong Password. Try Again.               
=================================================================
          _____                    _____                    _____          
         /\    \                  /\    \                  /\    \         
        /::\____\                /::\____\                /::\    \        
       /:::/    /               /::::|   |               /::::\    \       
      /:::/    /               /:::::|   |              /::::::\    \      
     /:::/    /               /::::::|   |             /:::/\:::\    \     
    /:::/____/               /:::/|::|   |            /:::/__\:::\    \    
   /::::\    \              /:::/ |::|   |            \:::\   \:::\    \   
  /::::::\    \   _____    /:::/  |::|___|______    ___\:::\   \:::\    \  
 /:::/\:::\    \ /\    \  /:::/   |::::::::\    \  /\   \:::\   \:::\    \ 
/:::/  \:::\    /::\____\/:::/    |:::::::::\____\/::\   \:::\   \:::\____\
\::/    \:::\  /:::/    /\::/    / ~~~~~/:::/    /\:::\   \:::\   \::/    /
 \/____/ \:::\/:::/    /  \/____/      /:::/    /  \:::\   \:::\   \/____/ 
          \::::::/    /               /:::/    /    \:::\   \:::\    \     
           \::::/    /               /:::/    /      \:::\   \:::\____\    
           /:::/    /               /:::/    /        \:::\  /:::/    /    
          /:::/    /               /:::/    /          \:::\/:::/    /     
         /:::/    /               /:::/    /            \::::::/    /      
        /:::/    /               /:::/    /              \::::/    /       
        \::/    /                \::/    /                \::/    /        
         \/____/                  \/____/                  \/____/       


1. Log in
2. Register
3. Exit
Enter option (1-3): 
```

## Reflection 

**Unit Testing**

The most important learning point is that, the team could have chose a **unit testing framework**, such as the`JUnit`. This would have saved a lot of time by automating `assetions` and test cases. Because not all members are familiar with unit testing, we have to manually re-compile and test individual edge cases, which costed us a lot of time. In fact, industrial practices dictate the use of unit test because merging untested or under-tested branches into production branches could lead to catastrophic result. In our case, it is many wasted time, but for a company, the entire service may have to be suspended temporarily for a emergency patch to be applied.

**Conclusion**

Reflecting on our project, integrating S.O.L.I.D. concepts not only solidified its technical
foundation but also improved teamwork efficiency. By establishing a modular and organized
codebase, we enabled smoother development cycles and minimized potential conflicts during
simultaneous work. Additionally, by implementing customized access to functionalities, we
achieved a clear division of user responsibilities and permissions, enhancing user experience
while ensuring data security and integrity. In summary, the thoughtful application of design
principles allowed us to achieve the project’s objectives effectively and efficiently.
