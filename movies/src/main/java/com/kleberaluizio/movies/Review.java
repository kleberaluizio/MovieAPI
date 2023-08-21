package com.kleberaluizio.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data // This annotation takes care of all getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    public Review (String body){
        this.body = body;
    }
    @Id
    private ObjectId id;
    private String body;
}
