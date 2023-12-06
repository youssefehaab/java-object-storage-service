# Java-object-storage
### Steps to run compose file
***
**1 - Build Jar**

- Run ```mvn clean install```

Jar file will be generated in a generated directory ```target```
***
**2 - Build Image**
- Run ```docker build -t java-object-storage .```

Image will be created with name ```java-object-storage```
***
**3 - Run compose file**
- Run ```docker-compose up -d```


### How to use API
***
**1 - set Content-Type: multipart/form-data**

**2 - The key in the request body must be named "object"**

