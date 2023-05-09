package com.nsdl.ndhm;

import com.nsdl.ndhm.controller.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class NdhmHealthidServiceApplicationTests {

    @Autowired
    AssistedHealthIdCreationByAadharController assistedHealthIdCreationByAadharController;

    @Autowired
    AssistedhealthIdCreationController assistedhealthIdCreationController;

    @Autowired
    CommonApiController CommonApiController;

    @Autowired
    ForgotHealthIdAndNumController forgotHealthIdAndNumController;

    @Autowired
    GatewayCallbackController gatewayCallbackController;

    @Autowired
    HealthIdCreationByAdharController healthIdCreationByAdharController;

    @Autowired
    HealthIdCreationByMobileNoController healthIdCreationByMobileNoController;

    @Autowired
    HealthIdProfileController healthIdProfileController;

    @Autowired
    HealthIdVerificationController healthIdVerificationController;

    @Autowired
    SearchApiController searchApiController;

    @Test
    void contextLoads() {
        Assertions.assertTrue(!assistedHealthIdCreationByAadharController.equals(null));
        Assertions.assertTrue(!assistedhealthIdCreationController.equals(null));
        Assertions.assertTrue(!CommonApiController.equals(null));
        Assertions.assertTrue(!forgotHealthIdAndNumController.equals(null));
        Assertions.assertTrue(!gatewayCallbackController.equals(null));
        Assertions.assertTrue(!healthIdCreationByAdharController.equals(null));
        Assertions.assertTrue(!healthIdCreationByMobileNoController.equals(null));
        Assertions.assertTrue(!healthIdProfileController.equals(null));
        Assertions.assertTrue(!healthIdVerificationController.equals(null));
        Assertions.assertTrue(!searchApiController.equals(null));
    }
}
