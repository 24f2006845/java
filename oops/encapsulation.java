class Human {
    private String name;
    private int age;

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
        h.setName("haider");
        h.setAge(21);
        System.out.println("Name: " + h.getName());
        System.out.println("Age: " + h.getAge());

    }
    
}
