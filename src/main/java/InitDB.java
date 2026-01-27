import org.hibernate.Session;

import config.HibernateUtil;

public class InitDB {

    public static void initialize() {
        System.out.println("===== INICIALIZANDO ESTRUCTURA DE BASE DE DATOS =====");
        
        Session session = null;

        try {
            // Al crear la sesión, Hibernate automáticamente crea las tablas
            // según la configuración hibernate.hbm2ddl.auto=update
            session = HibernateUtil.getSessionFactory().openSession();
            
            System.out.println("✓ Conexión establecida con la base de datos");
            System.out.println("✓ Tablas creadas/actualizadas automáticamente:");
            System.out.println("  - autors");
            System.out.println("  - llibres");
            System.out.println("  - prestecs");
            System.out.println("  - prestec_llibre");
            
            System.out.println("\n===== ESTRUCTURA DE BASE DE DATOS CREADA CORRECTAMENTE =====");

        } catch (Exception e) {
            System.err.println("Error al crear la estructura de la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
