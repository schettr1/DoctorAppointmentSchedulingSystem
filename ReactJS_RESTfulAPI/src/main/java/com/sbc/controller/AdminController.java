package com.sbc.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/reactjs_restful")  // not having @RequestMapping in controller gives "IllegalArgumentException: uriTemplate must not be null"
public class AdminController {

}
