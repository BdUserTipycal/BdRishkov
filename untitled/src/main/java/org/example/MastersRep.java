package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

public class MastersRep implements IRepository<Masters>{
    @PersistenceContext
    public EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();
    @Override
    @Transactional
    public void addObj(Masters master) throws SQLException {
        EntityTransaction transaction = em.getTransaction();
        try {
            em.persist(master);
            System.out.println("Мастер добавлен: " + master);
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении мастера: " + master);
        }

    }

    @Override
    @Transactional
    public void delete(long id) throws SQLException {
        Masters delMaster = em.find(Masters.class, id);
        if (delMaster != null) {
            delMaster.setExist(false);
            em.merge(delMaster);
            System.out.println("Клиент с id " + id + " был удален.");}
        else{
            System.out.println("Клиент с id " + id + "не найден");
        }

    }

    @Override
    @Transactional
    public void update(Masters master) throws SQLException {
        EntityTransaction transaction = em.getTransaction();
        try {
            Masters updatedMaster = em.merge(master);
            System.out.println("Мастер обновлен: " + updatedMaster);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка при обновлении мастера: " + master);
        }
    }

    @Override
    public Masters find(long id) throws SQLException {
        return em.find(Masters.class, id);
    }

    @Override
    public List<Masters> getAll() throws SQLException {
        return em.createQuery("SELECT m FROM Masters m", Masters.class).getResultList();
    }
}
