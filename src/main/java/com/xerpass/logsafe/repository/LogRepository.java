package com.xerpass.logsafe.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.xerpass.logsafe.models.Log;

public interface LogRepository extends PagingAndSortingRepository<Log, Integer>{

}
