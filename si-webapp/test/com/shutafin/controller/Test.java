package com.shutafin.controller;

public class Test extends BaseSpringTests{

    @org.junit.Test
    public void registration200Ok() throws Exception {
        String uri = "/users/registration/request";
        String bodyJSON = "{\"firstName\":\"bb\",\"lastName\":\"bb\",\"email\":\"bbhbbb\",\"password\":\"111111Zz\",\"userLanguageId\":\"1\"}";

        ApiResponse apiResponse = test(uri, bodyJSON);

        System.out.println("------apiResponse---------");
        System.out.println(apiResponse.getStatus());
        if (apiResponse.getError() != null){
            System.out.println(apiResponse.getError().getErrorMessage());
            System.out.println(apiResponse.getError().getErrorTypeCode());
            System.out.println(apiResponse.getError().getErrors());
        }
        System.out.println(apiResponse.getData());
    }

}
