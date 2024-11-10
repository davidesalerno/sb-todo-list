package net.davidesalerno.sample.todo_list.repository;

import net.davidesalerno.sample.todo_list.entity.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface LogRepository extends MongoRepository<Log, String> {
    public Log findByPath(String path);
    public List<Log> findByDate(Date date);
}
