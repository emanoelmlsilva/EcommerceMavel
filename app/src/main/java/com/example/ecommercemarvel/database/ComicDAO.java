package com.example.ecommercemarvel.database;

import io.requery.Entity;
import io.requery.Key;
import io.requery.Persistable;

@Entity
public interface ComicDAO extends Persistable {

    @Key
    int getId();

    String getTitle();
    String getDescription();
    String getImageUrl();
    Float getPrice();
    int getAmount();
    Boolean getRare();
}
