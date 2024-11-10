package net.davidesalerno.sample.todo_list.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document
@Data
@Builder
public class Log implements Serializable {
    @Id
    private String id;
    private String path;
    private Date date;
}
