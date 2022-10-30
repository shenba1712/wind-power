
# Technical Assessment  
## Task 
We would like you to help us build a service around exposing and correcting power data. On a frequent basis we aggregate data from our wind power parks informing us how much power was produced at a certain plant in a given time frame. This data is then further used on algorithms to provide value to our customers.  
The data we receive can potentially contain errors (e.g. malfunctioning sensors, errors in monitoring systems), hence we’d like the ability to manually overwrite certain datapoints in the process.  

Please take the `powerProduced.json` file as your “in-memory database” to build:

1. A REST endpoint to allow manual correction of the power generated of a given datapoint
2. A REST endpoint exposing all datapoints (with their potentially corrected values)

The challenge should be tackled with either Java or Kotlin, with the help of Spring Boot.  
While this technical challenge is an opportunity for you to showcase your skills, we also want to be respectful of your time and suggest spending no more than 3 hours on this. When implementing, make sure to follow known best practices around architecture, testability, and documentation.  

The result should be sent as a Git bundle (`git bundle create challenge.bundle --all`).

For any further questions please feel free to reach out at any time.


# Application Details

The application uses Java 11 and Spring Boot 2.7.5.
The `powerProduced.json` is loaded into H2 database using GSON. (Please find the details in ```TestData.java```)

To test the application easily, Swagger endpoints have been activated.
Please navigate to ```localhost:8080``` to use the application. 
Alternatively, you can also use Postman, Advanced Rest Controller or other API management platforms as well.

## Assumptions made

* Since the ID seems to be custom format, the assumption is that this ID is being sent along with other data.
* The Windpark stations are mapped as ENUM for now. If they have other details, it should be converted to a separate object.
* The Period field is assumed to be a String. Since the challenge doesn't involve any processing related to Period, it is stored as is.
If this field requires further processing, it can be changed to a suitable type like TemporalAmount.

## Design considerations
* The Created and Updated dates are handled by JPA using the annotation @CreatedData and @LastModifiedDate.
The assumption here is that these dates correspond to the values when the data is created and updated by the application in the database.
So, if some data point is corrected, then this updatedOn field is updated. This also means that the createdOn and updatedOn sent with the JSON is not considered.
* To identify exceptions properly, a custom error handler has been written. It can be extended with more exceptions in the future.
  * NoSuchElementExcception is mapped to 404 (Not Found) error.
  * IllegalArgumentException is mapped to 400 (Bad Request) error.
  * All other exceptions are mapped to 500 (Internal Server) error.
