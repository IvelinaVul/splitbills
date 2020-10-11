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
    public String add(User user)  {
        if (user == null) {
            throw new IllegalArgumentException("Parameter user cannot be null");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        if (!exists(user.getUsername())) {
            entityManager.persist(user);

        } else {
            entityManager.merge(user);

        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return user.getUsername();
    }

    private boolean exists(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User userWithSameUsername = entityManager.find(User.class, username);
        entityManager.getTransaction().commit();
        entityManager.close();

        return userWithSameUsername != null;

    }

    public boolean contains(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Parameter username cannot be null");
        }
        return exists(username);
    }

    @Override
    public User get(String username)  {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User found = entityManager.find(User.class, username);
        entityManager.getTransaction().commit();
        entityManager.close();
        return found;
    }
}
