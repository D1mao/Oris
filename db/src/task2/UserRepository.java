package task2;

import java.util.List;

public interface UserRepository extends CrudRepository<User>{
    List<User> findAllByAge(Integer age);
    List<User> findAllByCarMake(String carMake);
    List<User> findAllByHomeAddress(String homeAddress);

}
