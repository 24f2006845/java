class Human {
    private String name;
    private int age;

    public Human() {
        this.name = ""; // default constructor we can set the default values of name and age when creating an object of Human class
        this.age = 0;
    }
    public Human(String name, int age) {
        this.name = name; // parameterized constructor we can set the values of name and age when creating an object of Human class
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
public class encapsulation {
    public static  void main(String[] a) {
        Human h = new Human();
        Human h1 = new Human("ali", 20);
        System.out.println("Name: " + h1.getName());
        System.out.println("Age: " + h1.getAge());
        h.setName("haider");
        h.setAge(21);
        System.out.println("Name: " + h.getName());
        System.out.println("Age: " + h.getAge());

    }
    
}
