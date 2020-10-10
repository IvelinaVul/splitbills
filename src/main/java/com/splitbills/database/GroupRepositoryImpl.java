package com.splitbills.database;

import com.splitbills.database.models.Group;
import com.splitbills.database.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {

    private EntityManagerFactory entityManagerFactory;

    public GroupRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public int add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Parameter group cannot be null");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(group);
        entityManager.getTransaction().commit();
        entityManager.close();
        return group.getId();
    }

    @Override
    public List<Group> getAll(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User found = entityManager.find(User.class, username);
        List<Group> groups = found.getGroups();
        entityManager.getTransaction().commit();
        entityManager.close();
        return groups;
    }
}
