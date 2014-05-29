import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by bamboo on 29.05.14.
 */
public class Contacts {

    private ArrayList<Person> persons = new ArrayList<>();

    public Contacts() {

    }

    public void add(Person p) {
        persons.add(p);
    }

    public List<Person> filter(Predicate<Person> predicate) {
        ArrayList<Person> filtered = new ArrayList<>();

//        for (Person p : persons) {
//            if (predicate.test(p)) {
//                arr.add(new Person(p.getName()));
//            }
//        }
        persons.forEach(x -> {
            if (predicate.test(x)) {
                filtered.add(new Person(x.getName(), x.getAge()));
            }
        });

        return filtered;
    }
}
