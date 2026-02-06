package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtil;
import model.Autor;
import model.Llibre;
import model.Prestec;

public class AutorDAO {

    public void save(Autor autor) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.persist(autor); //persist añade al autor a la BD
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al añadir el autor: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }

    public Autor findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Autor autor = s.get(Autor.class, id);
        s.close();
        return autor;
    }

    public List<Autor> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Autor> autors = s.createQuery("FROM Autor", Autor.class).getResultList();
        s.close();
        return autors;
    }

    public void update(Autor autor) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.merge(autor);  // merge actualiza el objeto existente
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al actualizar el autor: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }

    public void delete(Autor autor) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            
            Autor autorPersistente = s.get(Autor.class, autor.getId());

            if (autorPersistente == null) {
                System.out.println("El autor no existe en la base de datos.");
                return;
            }

            //Revisa si hay libros con prestámos
            boolean tienePrestamos = autorPersistente.getLlibres().stream()
                    .anyMatch(llibre -> !llibre.getPrestecs().isEmpty());

            if (tienePrestamos) {
                System.out.println("Este autor tiene libros con préstamos.");
                System.out.print("¿Eliminar también los préstamos? (S/N): ");
                Scanner scanner = new Scanner(System.in); 
                String resp = scanner.nextLine();

                if (!resp.equalsIgnoreCase("S")) {
                    System.out.println("Eliminación cancelada.");
                    if (tx != null) tx.rollback(); 
                    return; 
                }

                //Borrar todos los prostemas asociados
                for (Llibre llibre : autorPersistente.getLlibres()) {
                    for (Prestec prestec : new ArrayList<>(llibre.getPrestecs())) {
                        prestec.getLlibres().remove(llibre); //se desvincula el libro
                        
                        if (prestec.getLlibres().isEmpty()) {
                            s.remove(prestec); //si no quedan libros, borra el prestamo
                        } else {
                            s.merge(prestec); //guarda los cambios
                        }
                    }
                }
            }
            s.remove(autorPersistente);
            tx.commit();
            System.out.println("Autor eliminado correctamente.");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error al eliminar el autor. Se ha hecho Rollback.");
            e.printStackTrace();
        } finally {
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
    }
}
