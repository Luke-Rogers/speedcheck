package com.speedcheck.service;

import com.speedcheck.repository.ResultRepository;
import com.speedcheck.transfer.Filters;
import com.speedcheck.transfer.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ResultsService {


    private final ResultRepository resultRepository;

    @Autowired
    private ResultsService(final ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Results getResults() {
        return new Results(resultRepository.findAll());
    }

    public Results getFilteredResults(final Filters filters) {
        return new Results(resultRepository.findFiltered(filters));
    }

    public void deleteResults(final Collection<Integer> toDelete) {
        resultRepository.deleteResults(toDelete);
    }
}
