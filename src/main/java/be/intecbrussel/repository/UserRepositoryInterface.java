package be.intecbrussel.repository;

import be.intecbrussel.model.Person;
import be.intecbrussel.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface {
    public boolean userExists(String username);

    public Optional<Person> getUser(String userName);

    public void addUser(User user);

    public List<Person> getPersonList();

    public void removeUser(String userName);

    public Optional<Person> login(String userName, String password);
}
