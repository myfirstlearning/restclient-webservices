package com.restclient.EmployeeClientTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.restclient.restclientwebservices.client.EmployeeRestClient;
import com.restclient.restclientwebservices.model.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EmployeeClientTest {

    @Test
    public void getForEntityTest(){

        ResponseEntity<Employee> entityResult = EmployeeRestClient.getForEntity(2);
        Assert.assertNotNull(entityResult);

    }
    @Test
    public void getAllTest(){

        List<Employee> empResult = EmployeeRestClient.getAll(3,6);
        Assert.assertNotNull(empResult);

    }

    @Test
    public void getAllTestForObject(){

        List<Employee> empResult = EmployeeRestClient.getAllForObject(3,6);
        Assert.assertNotNull(empResult);

    }

    @Test
    public void getAsJsonNodeTest() throws IOException {
        Employee employee = new Employee(4, "Max", "Simpson", 67000);
        JsonNode jsonNode = EmployeeRestClient.getAsJsonNode(4);

        Assert.assertNotNull(jsonNode);
        Assert.assertEquals(employee.getId(), jsonNode.path("id").asLong());
        Assert.assertEquals(employee.getFirstName(), jsonNode.path("firstName").asText());
        Assert.assertEquals(employee.getLastName(), jsonNode.path("lastName").asText());
        Assert.assertEquals(employee.getYearlyIncome(), jsonNode.path("yearlyIncome").asLong());

    }

    @Test
    public void postForObjectTest(){

        Employee employee = new Employee("Alex", "Carey", 87000);

        Employee createdEmployee = EmployeeRestClient.postForObject(employee);
        Assert.assertNotNull(createdEmployee);
        Assert.assertEquals(employee.getFirstName(), createdEmployee.getFirstName());
        Assert.assertEquals(employee.getLastName(), createdEmployee.getLastName());

    }

    @Test
    public void postForLocationTest(){

        Employee employee = new Employee("Nick", "Annam", 77000);
        URI uri = EmployeeRestClient.postForLocation(employee);
        Assert.assertNotNull(uri);

    }

    @Test
    public void postForEntityTest(){

        Employee employee = new Employee("Adam", "GillChrist", 97000);
        ResponseEntity<Employee> newEmployee = EmployeeRestClient.postForEntity(employee);
        Assert.assertNotNull(newEmployee);
    }

    @Test
    public void  headForHeadersTest(){

        HttpHeaders httpHeaders = EmployeeRestClient.headForHeaders();

        Assert.assertNotNull(httpHeaders);
        Assert.assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));

    }

    @Test
    public void optionsForAllowTest(){

        Set<HttpMethod> httpMethods = EmployeeRestClient.optionForAllow(1);

        List<HttpMethod> expectedHttpMethods = Arrays.asList(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE);

        Assert.assertNotNull(expectedHttpMethods);
        Assert.assertTrue(httpMethods.containsAll(expectedHttpMethods));


    }


}
