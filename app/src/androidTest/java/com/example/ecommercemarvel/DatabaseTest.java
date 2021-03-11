package com.example.ecommercemarvel;

import android.content.Context;

import com.example.ecommercemarvel.database.ComicDAOEntity;
import com.example.ecommercemarvel.database.ComicDAOImp;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {
    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule(MainActivity.class);

    public ComicDAOImp comicDAOImp;
    public ComicDAOEntity comicDAOEntity;

    @Before
    public void setUp(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        comicDAOImp = ComicDAOImp.getInstance(context);
        int idCurrent = comicDAOImp.getComics().get(comicDAOImp.getComics().size() - 1).getId();
        comicDAOEntity = new ComicDAOEntity();
        comicDAOEntity.setId(idCurrent + 1);
        comicDAOEntity.setTitle("Homem Aranha");
        comicDAOEntity.setRare(true);
        comicDAOEntity.setAmount(10);
        comicDAOEntity.setPrice(23f);
    }

    @Test
    public void insertItem(){
        int sizeOld = comicDAOImp.getComics().size();
        comicDAOImp.insertComic(comicDAOEntity);
        int sizeNew = comicDAOImp.getComics().size();
        assertTrue(sizeNew > sizeOld);
    }

    @Test
    public void updateItem() {
        ComicDAOEntity updateComic = comicDAOImp.getComicById(1);
        updateComic.setTitle("Thor");
        comicDAOImp.updateComic(updateComic);
        String titleUpdate = comicDAOImp.getComicById(1).getTitle();
        assertEquals(titleUpdate, "Thor");
    }
}