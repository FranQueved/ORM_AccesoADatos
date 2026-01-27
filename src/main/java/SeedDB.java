import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import config.HibernateUtil;
import model.Autor;
import model.Llibre;
import model.Prestec;

public class SeedDB {

    public static void seed() {
        System.out.println("===== LLENANDO BASE DE DATOS CON DATOS DE EJEMPLO =====");
        
        Session session = null;
        Transaction transaction = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // Verificar si ya hay datos
            Long count = session.createQuery("SELECT COUNT(a) FROM Autor a", Long.class).uniqueResult();
            if (count > 0) {
                System.out.println("⚠ La base de datos ya contiene datos. No se insertarán datos de ejemplo.");
                System.out.println("Si deseas volver a llenar la base de datos, elimina los datos manualmente primero.");
                transaction.commit();
                return;
            }

            System.out.println("Insertando datos de ejemplo...\n");

            // ========== CREAR AUTORES ==========
            System.out.println("Creando autores...");
            
            Autor cervantes = new Autor();
            cervantes.setNom("Miguel de Cervantes");

            Autor shakespeare = new Autor();
            shakespeare.setNom("William Shakespeare");

            Autor garciaMarquez = new Autor();
            garciaMarquez.setNom("Gabriel García Márquez");

            Autor borges = new Autor();
            borges.setNom("Jorge Luis Borges");

            Autor orwell = new Autor();
            orwell.setNom("George Orwell");

            // Persistir autores
            session.persist(cervantes);
            session.persist(shakespeare);
            session.persist(garciaMarquez);
            session.persist(borges);
            session.persist(orwell);

            System.out.println("✓ 5 autores creados");

            // ========== CREAR LIBROS ==========
            System.out.println("Creando libros...");
            
            Llibre quijote = new Llibre();
            quijote.setTitol("Don Quijote de la Mancha");
            quijote.setExemplarsDisponibles(5);
            quijote.setAutor(cervantes);

            Llibre novelas = new Llibre();
            novelas.setTitol("Novelas Ejemplares");
            novelas.setExemplarsDisponibles(3);
            novelas.setAutor(cervantes);

            Llibre hamlet = new Llibre();
            hamlet.setTitol("Hamlet");
            hamlet.setExemplarsDisponibles(4);
            hamlet.setAutor(shakespeare);

            Llibre macbeth = new Llibre();
            macbeth.setTitol("Macbeth");
            macbeth.setExemplarsDisponibles(2);
            macbeth.setAutor(shakespeare);

            Llibre cienAnos = new Llibre();
            cienAnos.setTitol("Cien años de soledad");
            cienAnos.setExemplarsDisponibles(6);
            cienAnos.setAutor(garciaMarquez);

            Llibre amor = new Llibre();
            amor.setTitol("El amor en los tiempos del cólera");
            amor.setExemplarsDisponibles(4);
            amor.setAutor(garciaMarquez);

            Llibre ficciones = new Llibre();
            ficciones.setTitol("Ficciones");
            ficciones.setExemplarsDisponibles(3);
            ficciones.setAutor(borges);

            Llibre aleph = new Llibre();
            aleph.setTitol("El Aleph");
            aleph.setExemplarsDisponibles(3);
            aleph.setAutor(borges);

            Llibre mil984 = new Llibre();
            mil984.setTitol("1984");
            mil984.setExemplarsDisponibles(7);
            mil984.setAutor(orwell);

            Llibre animalFarm = new Llibre();
            animalFarm.setTitol("Rebelión en la granja");
            animalFarm.setExemplarsDisponibles(5);
            animalFarm.setAutor(orwell);

            // Persistir libros
            session.persist(quijote);
            session.persist(novelas);
            session.persist(hamlet);
            session.persist(macbeth);
            session.persist(cienAnos);
            session.persist(amor);
            session.persist(ficciones);
            session.persist(aleph);
            session.persist(mil984);
            session.persist(animalFarm);

            System.out.println("✓ 10 libros creados");

            // ========== CREAR PRESTECS ==========
            System.out.println("Creando prestecs...");
            
            Prestec prestec1 = new Prestec();
            prestec1.setData(LocalDate.now().minusDays(10));
            prestec1.getLlibres().add(quijote);
            prestec1.getLlibres().add(hamlet);

            Prestec prestec2 = new Prestec();
            prestec2.setData(LocalDate.now().minusDays(5));
            prestec2.getLlibres().add(cienAnos);

            Prestec prestec3 = new Prestec();
            prestec3.setData(LocalDate.now().minusDays(2));
            prestec3.getLlibres().add(mil984);
            prestec3.getLlibres().add(ficciones);
            prestec3.getLlibres().add(macbeth);

            Prestec prestec4 = new Prestec();
            prestec4.setData(LocalDate.now().minusDays(15));
            prestec4.getLlibres().add(amor);
            prestec4.getLlibres().add(aleph);

            // Persistir prestecs
            session.persist(prestec1);
            session.persist(prestec2);
            session.persist(prestec3);
            session.persist(prestec4);

            System.out.println("✓ 4 prestecs creados");

            // Confirmar transacción
            transaction.commit();

            System.out.println("\n===== BASE DE DATOS LLENADA CORRECTAMENTE =====");
            System.out.println("Resumen de datos insertados:");
            System.out.println("  - 5 Autores");
            System.out.println("  - 10 Libros (2 por cada autor)");
            System.out.println("  - 4 Prestecs (con múltiples libros)");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al llenar la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
