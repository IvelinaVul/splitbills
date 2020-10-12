package com.splitbills.database;

import com.splitbills.database.models.Group;
import com.splitbills.database.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
        String hql = "SELECT G FROM Group G JOIN G.users U WHERE U.username= :username";
        Query query = entityManager.createQuery(hql, Group.class)
                .setParameter("username", username);

        List<Group> groups = query.getResultList();
        entityManager.close();
        return groups;
    }

}
