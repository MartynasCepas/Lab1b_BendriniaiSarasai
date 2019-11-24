/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab1b.Cepas;

/**
 *
 * @author Marty
 */
public class ItemMarket {
    public ItemList allCars = new ItemList();

    // suformuojamas sąrašas automobilių, kurie pagaminti vėliau nei riba
    public ItemList getNewerCars(int fromYear) {
        ItemList cars = new ItemList();
        for (Item c : allCars) {
            if (c.getYear() >= fromYear) {
                cars.add(c);
            }
        }
        return cars;
    }

    // suformuojamas sąrašas automobilių, kurių kaina yra tarp ribų
    public ItemList getByPrice(int fromPrice, int toPrice) {
        ItemList cars = new ItemList();
        for (Item c : allCars) {
            if (c.getPrice() >= fromPrice && c.getPrice() <= toPrice) {
                cars.add(c);
            }
        }
        return cars;
    }

    // suformuojamas sąrašas automobilių, turinčių didžiausią ridą
    public ItemList getHighestMileageCars() {
        ItemList cars = new ItemList();
        // formuojamas sąrašas su maksimalia reikšme vienos peržiūros metu
        double maxMileage = 0;
        for (Item c : allCars) {
            double mileage = c.getMileage();
            if (mileage >= maxMileage) {
                if (mileage > maxMileage) {
                    cars.clear();
                    maxMileage = mileage;
                }
                cars.add(c);
            }
        }
        return cars;
    }

    // suformuojams sąrašas automobilių, kurių modelio kodas atitinka nurodytą
    public ItemList getByMakeAndModel(String makeAndModel) {
        ItemList cars = new ItemList();
        for (Item c : allCars) {
            String carMakeAndModel = c.getMake() + " " + c.getModel();
            if (carMakeAndModel.startsWith(makeAndModel)) {
                cars.add(c);
            }
        }
        return cars;
    }
    // metodo main nėra -> demo bandymai klasėje AutomobiliuBandymai

}
