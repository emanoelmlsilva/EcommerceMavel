package com.example.ecommercemarvel;

import com.example.ecommercemarvel.model.Comic;
import com.example.ecommercemarvel.model.Prices;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ComicTest {

    Comic comic;

    @Before
    public void setUp(){
        Prices prices = new Prices();
        prices.setPrice(10f);
        List<Prices> pricesList = new ArrayList<>();
        pricesList.add(prices);

        comic = new Comic();
        comic.setId(1);
        comic.setTitle("Testando Comics");
        comic.setPrices(pricesList);
        comic.setRare(false);
    }

    @Test
    public void checkIsRare(){
        comic.setRare(true);
        assertTrue(comic.getRare());
    }

    @Test
    public void checkIsNotRare(){
        assertFalse(comic.getRare());
    }

    @Test
    public void checkPriceNotZero(){
        assertNotEquals(comic.getPrices().get(0).getPrice(), 0f);
    }

    @Test
    public void checkPriceBiggerZero(){
        assertTrue(comic.getPrices().get(0).getPrice() > 0);
    }

    @Test
    public void checkTitle(){
        assertEquals(comic.getTitle(), "Testando Comics");
    }
}



