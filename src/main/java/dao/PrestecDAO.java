package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtil;
import model.Llibre;
import model.Prestec;

public class PrestecDAO {
    public void crearPrestec(Prestec prestec, Llibre llibre) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();

            Llibre llibreBaseDatos = s.get(Llibre.class, llibre.getId());

            if (llibreBaseDatos.getExemplarsDisponibles() <= 0) { //comprueba el stock
                System.out.println("No quedan ejemplares disponibles de este libro.");
                throw new RuntimeException("Stock insuficiente"); 
            }

            llibreBaseDatos.setExemplarsDisponibles(llibreBaseDatos.getExemplarsDisponibles() - 1); //resta el stock
            s.merge(llibreBaseDatos);

            prestec.getLlibres().add(llibreBaseDatos);
            s.persist(prestec);
            tx.commit(); 
            System.out.println("Préstamo realizado!");

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); 
            }
            System.out.println("Se ha producido un error, se ha hecho rollback!");
            e.printStackTrace(); 
        } finally {
            s.close(); 
        }
    }

    public Prestec findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Prestec prestec = s.get(Prestec.class, id);
        s.close();
        return prestec;
    }

    public List<Prestec> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Prestec> prestecs = s.createQuery("SELECT DISTINCT p FROM Prestec p LEFT JOIN FETCH p.llibres", Prestec.class).getResultList();
        s.close();
        return prestecs;
    }

    public void update(Prestec prestec) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.merge(prestec);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
    }

    public void delete(Prestec prestec) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Prestec p = s.get(Prestec.class, prestec.getId());
            if (p != null) {
                s.remove(p);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
    }
    
    public void save(Prestec prestec) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.persist(prestec);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
    }
}