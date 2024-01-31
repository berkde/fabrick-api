package com.service.fabrickapi;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectPackages({
        "com.service.fabrickapi.integration",
        "com.service.fabrickapi.controller",
        "com.service.fabrickapi.service.implementation",
        "com.service.fabrickapi.entity.jpa",
        "com.service.fabrickapi.entity.json"
})
@SuiteDisplayName("Fabrick API Quality Insurance tests")
class FabrickApiTestSuit {

}
