package com.example.ecommercemarvel.database;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import io.requery.Persistable;
import io.requery.android.BuildConfig;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

public class ComicDAOImp {

    private ReactiveEntityStore<Persistable> dataStore;
    private static ComicDAOImp instance;

    private ComicDAOImp(ReactiveEntityStore<Persistable> data){
        dataStore = data;
    }

    private static ReactiveEntityStore<Persistable> getDataStore(Context context){
        DatabaseSource database = new DatabaseSource(context, Models.DEFAULT, 1);
        if (BuildConfig.DEBUG) {
            database.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }
        Configuration configuration = database.getConfiguration();
        return ReactiveSupport.toReactiveStore(new EntityDataStore<>(configuration));

    }

    public static ComicDAOImp getInstance(Context context){
        if(instance == null){
            instance = new ComicDAOImp(getDataStore(context));
        }
        return instance;
    }

    public List<ComicDAOEntity> getComics(){
        return dataStore.select(ComicDAOEntity.class).get().toList();
    }

    public ComicDAOEntity getComicById(int id){
        return dataStore.select(ComicDAOEntity.class).where(ComicDAOEntity.ID.eq(id)).get().firstOrNull();
    }

    public void insertComic(ComicDAOEntity comicDAOEntity){
        dataStore.insert(comicDAOEntity).subscribe();
    }

    public void updateComic(ComicDAOEntity comicDAOEntity){
        dataStore.update(comicDAOEntity);
    }

    public void deleteComicById(int id){
        dataStore.delete(ComicDAOEntity.class).where(ComicDAOEntity.ID.eq(id)).get().single();
    }
}
