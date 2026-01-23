package dao;

import config.HibernateUtil;
import model.Prestec;
import model.Llibre;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PrestecDAO {

    public void save(Prestec prestec) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(prestec);
        tx.commit();
        s.close();
    }

    public Prestec findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Prestec prestec = s.get(Prestec.class, id);
        s.close();
        return prestec;
    }

    public List<Prestec> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Prestec> prestecs = s.createQuery("FROM Prestec", Prestec.class).getResultList();
        s.close();
        return prestecs;
    }

    public void update(Prestec prestec) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(prestec);
        tx.commit();
        s.close();
    }

    public void delete(Prestec prestec) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.remove(prestec);
        tx.commit();
        s.close();
    }

    public void crearPrestec(Prestec prestec, Llibre llibre) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        // Persistir préstamo
        s.persist(prestec);

        // Asociar libro
        prestec.getLlibres().add(llibre);
        llibre.getPrestecs().add(prestec);

        tx.commit();
        s.close();
    }
}
