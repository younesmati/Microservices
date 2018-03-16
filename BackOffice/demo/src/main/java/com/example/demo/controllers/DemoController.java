package com.example.demo.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.RefFormeJuridique;
import com.example.demo.services.RefFormeJuridiqueService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author y.nadir
 *
 */
@RestController
@RequestMapping("/api")
@RefreshScope
@CrossOrigin("*")
public class DemoController {
    
    @Autowired
    RefFormeJuridiqueService refFormeJuridiqueService;
    
    @Value("${msg}")
    String msg;
    
    /*
     * http://host:demoAppPort/hystrix.stream to monitor in hystrix dashboard
     * the fallBack is called in case there is a delay or whatever problem
     * calling refFormeJuridiqueService.findAll() service
     */
    @HystrixCommand(fallbackMethod = "findAllFallBack")
    @GetMapping("/all")
    public List<RefFormeJuridique> findAll() {
        System.out.println("hi" + this.msg);
        return refFormeJuridiqueService.findAll();
    }
    
    @RequestMapping(value = "/formeJuridique/{id}", method = RequestMethod.GET)
    public RefFormeJuridique findById(@PathVariable("id") Long id) {
        return refFormeJuridiqueService.findById(id);
    }
    
    public List<RefFormeJuridique> findAllFallBack() {
        System.out.println("Hystrix Demo Rest Fall Back called");
        return Collections.emptyList();
    }
    
}
