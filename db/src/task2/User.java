package task2;

public class User {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String car_make;
    private String email;
    private String home_address;

    public User(Integer id, String firstName, String lastName, Integer age, String car_make, String email, String home_address) {
        this.id = id;
        this.name = firstName;
        this.surname = lastName;
        this.age = age;
        this.car_make = car_make;
        this.email = email;
        this.home_address = home_address;
    }

    public  Integer getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAge() {
        return age;
    }

    public String getCar_make() {
        return car_make;
    }

    public String getEmail() {
        return email;
    }

    public String getHome_address() {
        return home_address;
    }

}
