/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra demonstracinė automobilio klasė (jos objektai dedami į LinkedList)
 *       kuri realizuoja interfeisą Parsable<T>
 * IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį ir atitikimą Parsable. IŠBANDYKITE
 * jų veikimą, panaudojant klasę ItemTest. PASIRINKITE savo objektų
 * klasę ir sudarykite analogiškus metodus GERESNIAM ĮSISAVINIMUI rekomenduojame
 * pradėti nuo tuščios klasės
   ***************************************************************************
 */
package edu.ktu.ds.lab1b.Cepas;

import edu.ktu.ds.lab1b.util.Ks;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.ktu.ds.lab1b.util.Parsable;

public class Item implements Parsable<Item> {

    // bendri duomenys visiems automobiliams (visai klasei)
    final static private int minYear = 1994;

    final static private double minPrice = 200.0;
    final static private double maxPrice = 120_000.0;

    // kiekvieno automobilio individualūs duomenys
    private String type;
    private String category;
    private int date;
    private int bestBy;
    private double price;

    public Item() {
        
    }

    public Item(String type, String category,
            int date, int bestBy, double price) {
        this.type = type;
        this.category = category;
        this.date = date;
        this.bestBy = bestBy;
        this.price = price;
    }

    public Item(String data) {
        parse(data);
    }

    @Override
    public final void parse(String data) {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais
            Scanner ed = new Scanner(data);
            type = ed.next();
            category = ed.next();
            date = ed.nextInt();
            bestBy = ed.nextInt();
            setPrice(ed.nextDouble());
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas apie daikta -> " + data);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie daikta -> " + data);
        }
    }

    public String validate() {
        String error = "";
        int currentYear = LocalDate.now().getYear();
        if (date < minYear || date > currentYear) {
            error = "Netinkami gamybos metai, turi būti ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            error += " Kaina už leistinų ribų [" + minPrice
                    + ":" + maxPrice + "]";
        }
        return error;
    }

    @Override
    public String toString() {  // surenkama visa reikalinga informacija
        return String.format("%-8s %-8s %4d %7d %8.1f %s",
                type, category, date, bestBy, price, validate());
    }

    public String getMake() {
        return type;
    }

    public String getModel() {
        return category;
    }

    public int getYear() {
        return date;
    }

    public int getMileage() {
        return bestBy;
    }

    public double getPrice() {
        return price;
    }

    // keisti bus galima tik kainą - kiti parametrai pastovūs
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Item otherItem) {
        // lyginame pagal svarbiausią požymį - kainą
        double otherPrice = otherItem.getPrice();
        if (price < otherPrice) {
            return -1;
        }
        if (price > otherPrice) {
            return +1;
        }
        return 0;
    }
    // sarankiškai priderinkite prie Lambda funkcijų
    public final static Comparator<Item> byMakeAndModel
            = new Comparator<Item>() {
        @Override
        public int compare(Item car1, Item car2) {
            // pradžioje pagal markes, o po to pagal modelius
            int cmp = car1.getMake().compareTo(car2.getMake());
            if (cmp != 0) {
                return cmp;
            }
            return car1.getModel().compareTo(car2.getModel());
        }
    };
    public final static Comparator byPrice = new Comparator() {
        // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
        @Override
        public int compare(Object obj1, Object obj2) {
            double price1 = ((Item) obj1).getPrice();
            double price2 = ((Item) obj2).getPrice();
            // didėjanti tvarka, pradedant nuo mažiausios
            if (price1 < price2) {
                return -1;
            }
            if (price1 > price2) {
                return 1;
            }
            return 0;
        }
    };
    public final static Comparator byYearAndPrice = new Comparator() {
        // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
        @Override
        public int compare(Object obj1, Object obj2) {
            Item c1 = (Item) obj1;
            Item c2 = (Item) obj2;
            // metai mažėjančia tvarka, esant vienodiems lyginama price
            if (c1.getYear() < c2.getYear()) {
                return 1;
            }
            if (c1.getYear() > c2.getYear()) {
                return -1;
            }
            if (c1.getPrice() < c2.getPrice()) {
                return 1;
            }
            if (c1.getPrice() > c2.getPrice()) {
                return -1;
            }
            return 0;
        }
    };

    // metodas main = tiesiog paprastas pirminis automobilių išbandymas
    public static void main(String... args) {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Item a1 = new Item("Renault", "Laguna", 1997, 50000, 599);
        Item a2 = new Item("Renault", "Megane", 2015, 20000, 3500);
        Item a3 = new Item();
        Item a4 = new Item();
        a3.parse("Toyota Corolla 2001 20000 8500,8");
        a4.parse("Toyota Avensis 1990 20000  500,8");
        Ks.oun(a1);
        Ks.oun(a2);
        Ks.oun(a3);
        Ks.oun(a4);
    }
}
