package com.splitbills.database;

import com.splitbills.database.models.Debt;
import com.splitbills.database.models.Group;
import com.splitbills.database.models.PayRecord;
import com.splitbills.database.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {

    private EntityManagerFactory entityManagerFactory;

    public GroupRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Long addGroup(Group group) {
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
    public List<Group> getGroups(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Parameter username cannot be null");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT g " +
                " FROM User u " +
                " JOIN u.groups g  " +
                " WHERE u.username = :username");
        query.setParameter("username", username);
        List<Group> groups = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        if (groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }

    @Override
    public List<Debt> getDebts(String username, String groupname) throws NoSuchGroupException {
        if (username == null) {
            throw new IllegalArgumentException("Parameter username cannot be null");
        }
        if (groupname == null) {
            throw new IllegalArgumentException("Parameter groupname cannot be null");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT g.debts" +
                " FROM User u " +
                " JOIN u.groups g  " +
                " WHERE u.username = :username AND g.name = :groupname");
        query.setParameter("username", username);
        query.setParameter("groupname", groupname);
        List<Debt> debts = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
        if (debts == null) {
            throw new NoSuchGroupException();
        }
        return debts;
    }

    @Override
    public void updateDebt(Debt debt) {
        if (debt == null) {
            throw new IllegalArgumentException("Parameter debt cannot be null");

        }
        if (debt.getId() == null) {
            throw new IllegalArgumentException("Debt id cannot be null");

        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(debt);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<User> getUsers(String username, String groupname) throws NoSuchGroupException {
        if (username == null) {
            throw new IllegalArgumentException("Parameter username cannot be null");
        }
        if (groupname == null) {
            throw new IllegalArgumentException("Parameter groupname cannot be null");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT g.users " +
                " FROM User u " +
                " JOIN u.groups g  " +
                " WHERE u.username = :username AND g.name = :groupname");

        query.setParameter("username", username);
        query.setParameter("groupname", groupname);

        List<User> users = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        if (users == null) {
            throw new NoSuchGroupException();
        }
        return users;
    }

    @Override
    public void addPayRecord(String username, String groupname, PayRecord payRecord) throws NoSuchGroupException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT g " +
                " FROM User u " +
                " JOIN u.groups g  " +
                " WHERE u.username = :username AND g.name = :groupname");

        query.setParameter("username", username);
        query.setParameter("groupname", groupname);

        Group group = (Group) query.setMaxResults(1).getSingleResult();
        if (group == null) {
            entityManager.getTransaction().commit();
            entityManager.close();
            throw new NoSuchGroupException();
        }
        group.addPayRecord(payRecord);
        entityManager.merge(group);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
