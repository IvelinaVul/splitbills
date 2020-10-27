package com.splitbills.database;

import com.splitbills.database.models.Group;
import com.splitbills.database.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {

    private EntityManagerFactory entityManagerFactory;

    public GroupRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Long add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Parameter group cannot be null");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(group);
        entityManager.getTransaction().commit();
        entityManager.close();
        return group.getId();
    }
    @Override
    public List<Group> getAll(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Parameter username cannot be null");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, username);
        entityManager.getTransaction().commit();
        entityManager.close();
        if (user != null) {
            return user.getGroups();
        }
        return new ArrayList<>();
    }

}
