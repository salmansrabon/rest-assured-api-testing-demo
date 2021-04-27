import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Customer {
    Properties prop=new Properties();
    FileInputStream file;
    {
        try
        {
            file = new FileInputStream("./src/test/resources/config.properties");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String token;
    public void callingLoginAPI() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body(
                                "{\"username\":\"salman\",\n" +
                        "    \"password\":\"salman1234\"}"
                        ).
                        when()
                        .post("/customer/api/v1/login").
                        then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath jsonpath = res.jsonPath();
        token = jsonpath.get("token");
        Utils.setEnvVariable(token);
    }
    public void customerList() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token")).
                                when()
                        .get("/customer/api/v1/list").
                                then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("Customers[0].id").toString(),"101");
        System.out.println(res.asString());
    }

}
