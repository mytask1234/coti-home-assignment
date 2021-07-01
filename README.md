## Coti Home Assignment ( https://coti.io )

To build:
```
mvn clean package
```

To run:
```
java -jar target/coti-home-assignment-0.0.1-SNAPSHOT.jar
```

### The requirements:

Create a REST server that will handle insert/delete of data into database and keeping an interval enabled array of the data.  
General: user will call API to insert values to array, also, those will be written to the database.  
  
  
Create an endpoint (/insert) that should:  
    - Receive an un-signed number.  
    - Log it to a database.  
    - Output a sum of numbers that were logged to the database.  
    - Output the sum of all values divided by the input value.  
  
  
Create an endpoint /delete that should:  
    - Receive index of value to remove (index in the database)  
    - Swap last value in database with value in index place  
    - Replace in interval enabled array index place with -1  
  
  
Create an endpoint (/return) that should:  
    - Get index of rows.  
    - Return space interval index values.  
  
  
Development should:  
    - handle errors and exceptions  
    - performance  
    - edge-cases  
    - logging  
    - unit test  
  
  
Examples (examples are independent one from other):  
1. /insert output example  
assuming that input was 5 and also previous inputs were 1,2,3 then:  
		{ "output1": 11 , "output2" : 2 }  
<br />
2. at first, interval enabled array is []  
user call /insert value=4  
array [4] database [4]  
user call /insert value=5  
array [4,5] database [4,5]  
user call /insert value=123  
array [4,5,123] database [4,5,123]  
user call /delete index=1  
array [4,-1,123] database [4,123,5]  
user call /return, with values=0,1,2  
it will return [4,-1,123]  
  
  
Upload the solution to your git account and reply with the details  
  
  
