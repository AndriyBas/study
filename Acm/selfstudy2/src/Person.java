/**
 * Created by bamboo on 29.05.14.
 */
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", getName(), getAge());
    }

    public int getAge() {
        return age;
    }

    public boolean testOlolo(MyFuncInterface f) {
        return f.doOlolo(this);
    }
}
