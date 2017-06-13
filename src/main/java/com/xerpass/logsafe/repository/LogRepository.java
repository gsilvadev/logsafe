package com.xerpass.logsafe.repository;

import org.springframework.data.repository.CrudRepository;

import com.xerpass.logsafe.models.Log;

public interface LogRepository extends CrudRepository<Log, Integer>{

}
