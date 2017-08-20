package com.speedcheck.controller;

import com.speedcheck.service.ResultsService;
import com.speedcheck.transfer.Filters;
import com.speedcheck.transfer.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultsService resultsService;

    @Autowired
    private ResultController(final ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Results getResults() {
        return resultsService.getResults();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Results getFilteredResults(@RequestBody Filters filters) {
        return resultsService.getFilteredResults(filters);
    }
}
