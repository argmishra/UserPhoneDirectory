User Phone Directory
==========  
        
## Summary

Create a Spring Boot RESTful Web Service application that will be used to manage two data types: 

1. User: A user of the system 
2. Phone: A phone belonging to a user. Each user can have 0 to many phones. 

Both types will need to be persisted by the application in the storage medium of your choice. 
A good starting point for this exercise is spring initializr. This will generate a starter Spring Boot
project for you. The output of this exercise can be checked into a repository or bundled up and sent to
us directly. The exercise will be discussed in the interview. 

A full and comprehensive implementation is not expected to be delivered. Focus on what you believe is most important. 
Data Types 

The following are representations of the two data types. These are not final and can be tailored to suit your application as needed. User: 
```curl
{ 
"userId":"93f3ed0a-92bd-4c82-ba0e-c098111cef59", 
"userName":"john_user", 
"password":"*****", 
"emailAddress":"john@example.com", 
"preferredPhoneNumber":"+353881234567" 
} 
phoneId is a UUID 
userName is a String 
password is a String 
emailAddress is a String 
preferredPhoneNumber is a String 
Phone: 
{ 
"phoneId":"2a810635-18d0-40ce-ada6-0c0fd1181225", 
"phoneName":"John's Pixel", 
"phoneModel":"ANDROID", 
"phoneNumber":"+353881234567" 
} 
phoneId is a UUID 
phoneName is a String 
phoneNumber is a String 
phoneModel can be any of: [IPHONE, ANDROID, DESK_PHONE, SOFT_PHONE] 
```
## Application Functionality 
The following are tasks that the application could support: 

1. Add a user to the system 
2. Delete a user from the system 
3. List users in the system 
4. Add a phone to a user 
5. Delete a user's phone 
6. List a user's phones 
7. Update a user's preferred phone number 

Things to consider for your application 
1. Testing 
2. Logging 
3. Basic Authentication & Authorization if appropriate 
4. Documentation 
5. Running instructions


## Solution
1. Add User - Implemented  
2. Delete User - Implemented
3. List Users - Implemented
4. Add phone to User- Implemented
5. Delete user phone - Implemented
6. Update user phone - Implemented
7. List User phones - Implemented
8. Actuator end point - Implemented (http://localhost:8080/actuator)
9. Swagger Integration - Implemented (http://localhost:8080/swagger-ui.html)
10. Generic Exception Handling and Validations - Implemented
11. In Memory Database - Implemented
12. Run Script - Implemented
13. Unit Testing - Implemented
14. Integration Testing - 
15. Code Coverage - Implemented
16. Basic Authentication - 

```curl
curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d '{ "userName":"anurag", "emailAddress":"anurag@example.com", "preferredPhoneNumber":"+353881234567" }'

curl -X GET "http://localhost:8080/users"

curl -X DELETE "http://localhost:8080/users/7f000101-7b85-11e2-817b-8542026e0000"

curl -X POST "http://localhost:8080/users/7f000101-7b85-11e2-817b-8542026e0000/phone" -H "Content-Type: application/json" -d '{ "phoneName":"Nigma", "phoneModel":"ANDROID", "phoneNumber":"+353 94188847829" }'

curl -X GET "http://localhost:8080/users/7f000101-7b85-11e2-817b-8542026e0000/phone"

curl -X DELETE "http://localhost:8080/phones/7f000101-7b85-1c0b-817b-853cc6ae0002"

curl -X PUT "http://localhost:8080/phones/7f000101-7b85-1c0b-817b-853cc6ae0002" -H "Content-Type: application/json" -d '{ "phoneName":"Grimloack", "phoneModel":"IPHONE", "phoneNumber":"+353 946077680" }'
```

