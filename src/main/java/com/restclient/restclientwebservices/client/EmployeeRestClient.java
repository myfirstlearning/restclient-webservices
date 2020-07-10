package com.restclient.restclientwebservices.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restclient.restclientwebservices.model.Employee;
import com.restclient.restclientwebservices.util.BasicTimeMonitoring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

public class EmployeeRestClient {

    private static final String RESOURCE_PATH = "/rest/employees"; //=== Rest End Point
    private static final String HOST = "http://localhost"; //== local host
    private static final int PORT = 8080;

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeRestClient.class);
    private static String REQUEST_URI;
    private static RestTemplate restTemplate;


    public EmployeeRestClient(RestTemplate restTemplate, String host, int port){
        this.restTemplate = restTemplate;
        this.REQUEST_URI = host + ":" + port + RESOURCE_PATH;
    }

    public static void populateRequestURI(){
        //EmployeeRestClient employeeRestClient = new EmployeeRestClient(new RestTemplate(),"http://localhost",8080);
        EmployeeRestClient employeeRestClient = new EmployeeRestClient(new RestTemplate(),HOST,PORT);
    }


    //================ GET REQUEST ================
    //=============================================

    /**
     * Get the specific employee detail
     * getForEntity -> returns resource body, header and status code
     * @param id
     * @return
     */
    public static ResponseEntity<Employee> getForEntity(long id){

        populateRequestURI();
        ResponseEntity<Employee> entity = restTemplate.getForEntity(REQUEST_URI + "/{id}", Employee.class, "2");

        LOG.info("Status code value: " + entity.getStatusCode());
        LOG.info("HTTP Header 'ContentType' : " + entity.getHeaders().getContentType());

        return entity;
    }

    /**
     * Get all employess
     * getForEntity -> returns resource body, headers, status code
     * @param page
     * @param pageSize
     * @return
     */
    public static List<Employee> getAll(int page, int pageSize) {

        populateRequestURI();
        String requestUri = REQUEST_URI + "?page={page}&pageSize={pageSize}";
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("page", Integer.toString(page));
        urlParameters.put("pageSize", Long.toString(pageSize));
        ResponseEntity<Employee[]> entity = restTemplate.getForEntity(requestUri, Employee[].class,
                urlParameters);
        LOG.info("Status code value: " + entity.getStatusCode());
        LOG.info("HTTP Header 'ContentType' : " + entity.getHeaders().getContentType());
        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();

    }

    /**
     * getForObject -> returns only the specific Employee Object
     * @param id
     * @return
     */
    public Optional<Employee> getForObject(long id){

        populateRequestURI();
        Employee employee = restTemplate.getForObject(REQUEST_URI + "/{id}", Employee.class, Long.toString(id));

        return Optional.ofNullable(employee);
    }

    /**
     * Get all employess
     * getForObject -> returns only the resource(List<Employee>)
     * @param page
     * @param pageSize
     * @return
     */
    public static List<Employee> getAllForObject(int page, int pageSize) {

        populateRequestURI();
        String requestUri = REQUEST_URI + "?page={page}&pageSize={pageSize}";
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("page", Integer.toString(page));
        urlParameters.put("pageSize", Long.toString(pageSize));
        List<Employee> entity = restTemplate.getForObject(requestUri, List.class, urlParameters);

        return entity;
    }

    /**
     * Operates on JSON string
     * @param id
     * @return
     * @throws IOException
     */
    public static JsonNode getAsJsonNode(long id) throws IOException {

        BasicTimeMonitoring timeMonitor = new BasicTimeMonitoring();
        timeMonitor.start();

        populateRequestURI();
        String jsonString = restTemplate.getForObject(REQUEST_URI + "/{id}" , String.class, id);

        ObjectMapper mapper = new ObjectMapper();

        LOG.info(">>>>>>>>>> Time elapsed to get the Employee : " + timeMonitor.stop());

        return mapper.readTree(jsonString);

    }


    //=============== POST REQUEST ================
    //=============================================


    /**
     * postForObject -> creates new Employee object
     * and returns the newly created Employee
     * @param emploee
     * @return
     */
    public static Employee postForObject(Employee emploee){

        populateRequestURI();
        Employee newEmployee = restTemplate.postForObject(REQUEST_URI, emploee, Employee.class);
        return newEmployee;
    }

    /**
     * postForLocation -> creates new Employee Object
     * and return the Location URI
     * @param employee
     * @return
     */
    public static URI postForLocation(Employee employee){

        populateRequestURI();
        URI uri = restTemplate.postForLocation(REQUEST_URI, employee);
        return uri;

    }

    /**
     * postForEntity -> creates a newEmploee
     * sends and receives Resource body, status code and headers
     * @param employee
     * @return
     */
    public static ResponseEntity<Employee> postForEntity(Employee employee){

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("User-Agent", "EmployeeRestClient demo class");
        headers.add("Accept-Language", "en-US");

        HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

        populateRequestURI();
        ResponseEntity<Employee> employeeEntity = restTemplate.postForEntity(REQUEST_URI, entity, Employee.class);

        return employeeEntity;
    }

    //=============== PUT REQUEST =================
    //=============================================


    /**
     * putEmployeeDetails -> updates the employee details
     * @param employee
     */
    public static void putEmployeeDetails(Employee employee){

        populateRequestURI();
        restTemplate.put(REQUEST_URI + "/{id}", employee, Long.toString(employee.getId()));

    }

    /**
     * putWithExchange -> updates employee details
     * gives additional information about status code, HTTP headers and Resource body
     * @param employee
     * @return
     */
    public static ResponseEntity<Employee> putWithExchange(Employee employee){

        populateRequestURI();
        ResponseEntity<Employee> employeeEntity = restTemplate.exchange(REQUEST_URI + "/{id}",
                                                                                HttpMethod.PUT,
                                                                                new HttpEntity<>(employee),
                                                                                Employee.class,
                                                                                Long.toString(employee.getId()));

        return employeeEntity;
    }


    //=============== DELETE REQUEST ==============
    //=============================================

    /**
     * deleteEmployee -> delete the specified employee
     * @param id
     */
    public static void deleteEmployee(long id){

        populateRequestURI();
        restTemplate.delete(REQUEST_URI + "/{id}", Long.toString(id));

    }

    /**
     * deleteWithExchange -> delete the specified employee
     * @param id
     * @return
     */
    public static ResponseEntity<Void> deleteWithExchange(long id){

        populateRequestURI();
        ResponseEntity<Void> employee = restTemplate.exchange(REQUEST_URI + "/{id}", HttpMethod.DELETE, null, void.class, Long.toString(id));

        return employee;

    }



    //=============== HEAD REQUEST ================
    //=============================================

    public static HttpHeaders headForHeaders(){

        populateRequestURI();
        HttpHeaders httpHeaders = restTemplate.headForHeaders(REQUEST_URI);

        return httpHeaders;
    }

    //=============== OPTION REQUEST ==============
    //=============================================

    public static Set<HttpMethod>  optionForAllow(long id){

        populateRequestURI();
        Set<HttpMethod> optionsAvaliable = restTemplate.optionsForAllow(REQUEST_URI + "/{id}", Long.toString(id));

        return optionsAvaliable;

    }


}
