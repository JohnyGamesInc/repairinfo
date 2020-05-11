package com.atmcasesapp.repositories;

import com.atmcasesapp.entity.AtmCase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmCasesRepository extends CrudRepository<AtmCase, Long> {

    @Query("SELECT caseDescription FROM AtmCase atmcase GROUP BY case_desc ORDER BY count(case_desc) desc")
    List<String> findCausesOrderedByFreq();

    List<AtmCase> findAllByCaseDescriptionIn(List<String> caseDescriptions);

    @Query("SELECT atmcase from AtmCase atmcase order by (finish - start) desc")
    List<AtmCase> findLongestFixes();

    @Query("SELECT atmcase from AtmCase atmcase " +
            "WHERE caseDescription IN (SELECT caseDescription from AtmCase group by caseDescription having count(caseDescription) > 2) " +
            "ORDER BY case_desc, case_id asc")
    List<AtmCase> findRepeatedCauseOrderedByCauseAndCaseId();
}
