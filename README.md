# file-converter-api

A Spring Boot application using Gradle and Java focused on convert the content of a file to json.

Each line in the file is a registry and the pattern of each registry must follow the table below:

| campo            | tamanho | tipo                         |
|------------------|---------|------------------------------|
| id usuário       | 10      | numérico                     |
| nome             | 45      | texto                        |
| id pedido        | 10      | numérico                     |
| id produto       | 10      | numérico                     |
| valor do produto | 12      | decimal                      |
| data compra      | 8       | numérico (formato: yyyymmdd) |

Example:

    |-userId--|--------------userName----------------------|-orderId-|-prodId--|---value---|-date--|
    0000000070                                  Paula Teste00000007530000000003     1836.7420210308

## 🚀 Stack

- **Java 21**
- **Spring Boot**
- **Gradle**
- **Docker**

## 📦 Build and Run with Docker

1. **Build the Docker image from the root of the project**
   (if you don't have docker installed just use the link in the references at the end of this readme)
   ```sh
   docker build -t <image_name> -f devops/Dockerfile .

2. **Run the Docker container**
    ```sh
   docker run -d -p 8080:8080 --name file-converter-api <image_name>

3. **Access the swagger endpoint through the browser**
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

4. **Select the endpoint `/files` and click on `Try it out` **

### **References**

**Docker** – [get started page](https://www.docker.com/get-started/)