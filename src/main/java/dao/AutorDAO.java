package dao;

import config.HibernateUtil;
import model.Autor;
import model.Llibre;
import model.Prestec;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutorDAO {

    public void save(Autor autor) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(autor);
        tx.commit();
        s.close();
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
        Transaction tx = s.beginTransaction();
        s.merge(autor);  // merge actualiza el objeto existente
        tx.commit();
        s.close();
    }

    public void delete(Autor autor) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        // Revisa si hay libros con préstamos
        boolean tienePrestamos = autor.getLlibres().stream()
                .anyMatch(llibre -> !llibre.getPrestecs().isEmpty());

        if (tienePrestamos) {
            System.out.println("Este autor tiene libros con préstamos.");
            System.out.print("¿Eliminar también los préstamos? (S/N): ");
            String resp = new Scanner(System.in).nextLine();
            if (!resp.equalsIgnoreCase("S")) {
                System.out.println("Eliminación cancelada.");
                s.close();
                return;
            }

            // Borrar todos los préstamos asociados
            for (Llibre llibre : autor.getLlibres()) {
                for (Prestec prestec : new ArrayList<>(llibre.getPrestecs())) {
                    prestec.getLlibres().remove(llibre); // desvincular libro
                    if (prestec.getLlibres().isEmpty()) {
                        s.remove(prestec); // si no quedan libros, borrar el prestamo
                    } else {
                        s.persist(prestec); // guardar cambios
                    }
                }
            }
            s.remove(autor);
            tx.commit();
            s.close();
            System.out.println("Autor y libros eliminados correctamente.");
        }
    }
}
