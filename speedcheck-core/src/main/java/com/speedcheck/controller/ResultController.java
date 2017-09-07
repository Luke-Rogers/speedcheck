package com.speedcheck.controller;

import com.speedcheck.service.ResultsService;
import com.speedcheck.transfer.Filters;
import com.speedcheck.transfer.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteResults(@RequestParam("id") Collection<Integer> toDelete) {
        resultsService.deleteResults(toDelete);
    }
}
