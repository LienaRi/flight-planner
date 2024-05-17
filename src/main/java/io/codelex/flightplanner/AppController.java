package io.codelex.flightplanner;

import org.springframework.web.bind.annotation.*;

@RestController
public class AppController {

    @RequestMapping("/api/**")
    public void apiRequest() {
    }

    @RequestMapping("/testing-api/**")
    public void testingApiRequest() {
    }





}
