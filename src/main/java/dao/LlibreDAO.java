package dao;

import config.HibernateUtil;
import model.Llibre;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LlibreDAO {

public void save(Llibre llibre) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.persist(llibre);
            tx.commit(); 
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al añadir el libro: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }

    public Llibre findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Llibre llibre = s.get(Llibre.class, id);
        s.close();
        return llibre;
    }

    public List<Llibre> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Llibre> llibres = s.createQuery("FROM Llibre", Llibre.class).getResultList();
        s.close();
        return llibres;
    }

    public List<Llibre> findByAutor(String nomAutor) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Llibre> result = s.createQuery(
                        "FROM Llibre l WHERE l.autor.nom = :nom",
                        Llibre.class)
                .setParameter("nom", nomAutor)
                .getResultList();
        s.close();
        return result;
    }

    public void update(Llibre llibre) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.merge(llibre);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al actualizar libro: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }

    public void delete(Llibre llibre) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();

            if (llibre != null) {
                s.remove(llibre);
            }
            tx.commit();
            
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al eliminar libro: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }
}
