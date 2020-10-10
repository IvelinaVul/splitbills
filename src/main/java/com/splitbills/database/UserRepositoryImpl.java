package com.splitbills.database;

import com.splitbills.database.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UserRepositoryImpl implements UserRepository {

    private EntityManagerFactory entityManagerFactory;

    public UserRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void add(User user) throws UserAlreadyExistsException {
        if (user == null) {
            throw new IllegalArgumentException("Parameter user cannot be null");
        }
        if (exists(user)) {
            throw new UserAlreadyExistsException("User with the same username exists");
        } else {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    private boolean exists(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User userWithSameUsername = entityManager.find(User.class, user.getUsername());
        entityManager.getTransaction().commit();
        entityManager.close();

        return userWithSameUsername != null;

    }

    public boolean contains(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Parameter user cannot be null");
        }
        return exists(user);
    }

    @Override
    public User get(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User found = entityManager.find(User.class, username);
        entityManager.getTransaction().commit();
        entityManager.close();
        return found;
    }
}
