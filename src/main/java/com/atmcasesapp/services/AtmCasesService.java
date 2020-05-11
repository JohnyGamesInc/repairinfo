package com.atmcasesapp.services;

import com.atmcasesapp.entity.AtmCase;
import com.atmcasesapp.repositories.AtmCasesRepository;
import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AtmCasesService {
    @Autowired
    private AtmCasesRepository casesRepository;

    public void saveAllCases(List<AtmCase> atmCases) {
        casesRepository.saveAll(atmCases);
    }

    public void deleteAllCases() {
        casesRepository.deleteAll();
    }

    public List<AtmCase> findAllCases() {
        return (List<AtmCase>) casesRepository.findAll();
    }

    public List<AtmCase> findTopThreeCause() {
        var cases = casesRepository.findCausesOrderedByFreq();
        var casesDescriptions = cases.size() < 3 ? cases : cases.subList(0, 3);
        var outputList = casesRepository.findAllByCaseDescriptionIn(casesDescriptions);
        outputList.sort(Comparator.comparing(AtmCase::getCaseDescription)
                .thenComparing(AtmCase::getCaseId));
        return outputList;
    }

    public List<AtmCase> findTopThreeLongestFixes() {
        var cases = casesRepository.findLongestFixes();
        return cases.size() < 3 ? cases : cases.subList(0, 3);
    }

    public Collection<AtmCase> findRepeatedCauseCases() {
        var cases = casesRepository.findRepeatedCauseOrderedByCauseAndCaseId();
        return generateOutputList(cases);
    }

    private Collection<AtmCase> generateOutputList(List<AtmCase> cases) {
        Set<AtmCase> outputList = new TreeSet<>(Comparator.comparing(AtmCase::getCaseDescription)
                .thenComparing(AtmCase::getCaseId));
        Map<String, List<AtmCase>> casesByCause = cases.stream()
                .collect(Collectors.groupingBy(AtmCase::getCaseDescription));
        casesByCause.forEach((k, v) -> {
            v.sort(Comparator.comparing(AtmCase::getCaseId));
            for (int i = 0; i < v.size() - 1; i++) {
                if (calculatePeriod(v.get(i), v.get(i + 1)) <= 15) {
                    outputList.add(v.get(i));
                    outputList.add(v.get(i + 1));
                }
            }
        });
        return outputList;
    }

    private long calculatePeriod(AtmCase caseCurrent, AtmCase caseNext) {
        long period = ChronoUnit.DAYS.between(caseCurrent.getFinish().toLocalDate(), caseNext.getStart().toLocalDate());
        System.out.println(period);
        return period;
    }
}
