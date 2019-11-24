/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab1b.Cepas;

import java.util.Random;
import edu.ktu.ds.lab1b.util.ParsableList;

/**
 *
 * @author Marty
 */
public class ItemList extends ParsableList<Item>{
     public ItemList() {
    }
    
    public ItemList(int count) {
        super();
        String[][] makesAndModels = {
            {"Mazda", "121", "323", "626", "MX6"},
            {"Ford", "Fiesta", "Escort", "Focus", "Sierra", "Mondeo"},
            {"Saab", "92", "96"},
            {"Honda", "Accord", "Civic", "Jazz"},
            {"Renault", "Laguna", "Megane", "Twingo", "Scenic"},
            {"Peugeot", "206", "207", "307"}
        };
        Random rnd = new Random();
        rnd.setSeed(2000);
        for (int i = 0; i < count; i++) {
            int makeIndex = rnd.nextInt(makesAndModels.length);
            int modelIndex = rnd.nextInt(makesAndModels[makeIndex].length - 1) + 1;
            add(new Item(makesAndModels[makeIndex][0], makesAndModels[makeIndex][modelIndex],
                    1994 + rnd.nextInt(20),
                    6000 + rnd.nextInt(222_000),
                    1000 + rnd.nextDouble() * 100_000));
        }
    }
    
    @Override
    protected Item createElement(String data) {
        return new Item(data);
    }
}
