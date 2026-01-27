import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import dao.AutorDAO;
import dao.LlibreDAO;
import dao.PrestecDAO;
import model.Autor;
import model.Llibre;
import model.Prestec;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final AutorDAO autorDAO = new AutorDAO();
    private static final LlibreDAO llibreDAO = new LlibreDAO();
    private static final PrestecDAO prestecDAO = new PrestecDAO();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("0. Inicializar/Llenar Base de Datos");
            System.out.println("1. Autor");
            System.out.println("2. Llibre");
            System.out.println("3. Prestec");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 0 -> menuInicializacion();
                case 1 -> menuAutor();
                case 2 -> menuLlibre();
                case 3 -> menuPrestec();
                case 4 -> exit = true;
                default -> System.out.println("Opción no válida");
            }
        }
        System.out.println("Saliendo de la aplicación...");
        sc.close();
    }

    // =========================
    // MENÚ INICIALIZACIÓN
    // =========================
    private static void menuInicializacion() {
        System.out.println("\n--- INICIALIZACIÓN DE BASE DE DATOS ---");
        System.out.println("1. Crear estructura (InitDB)");
        System.out.println("2. Llenar con datos (SeedDB)");
        System.out.println("3. Crear estructura y llenar");
        System.out.println("0. Volver");
        System.out.print("Selecciona una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1 -> InitDB.initialize();
            case 2 -> SeedDB.seed();
            case 3 -> {
                InitDB.initialize();
                SeedDB.seed();
            }
            case 0 -> {}
            default -> System.out.println("Opción no válida");
        }
    }

    // =========================
    // MENÚ AUTOR
    // =========================
    private static void menuAutor() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MENÚ AUTOR ---");
            System.out.println("1. Añadir Autor");
            System.out.println("2. Listar Autores");
            System.out.println("3. Modificar Autor");
            System.out.println("4. Eliminar Autor");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> crearAutor();
                case 2 -> listarAutores();
                case 3 -> modificarAutor();
                case 4 -> eliminarAutor();
                case 5 -> back = true;
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private static void crearAutor() {
        System.out.print("Nombre del autor: ");
        String nom = sc.nextLine();
        Autor autor = new Autor();
        autor.setNom(nom);
        autorDAO.save(autor);
        System.out.println("Autor creado con ID: " + autor.getId());
    }

    private static void listarAutores() {
        List<Autor> autors = autorDAO.findAll();
        System.out.println("=== Lista de Autores ===");
        for (Autor a : autors) {
            System.out.println("ID: " + a.getId() + " | Nombre: " + a.getNom());
        }
    }

    private static void modificarAutor() {
        System.out.print("ID del autor a modificar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Autor autor = autorDAO.findById(id);
        if (autor == null) {
            System.out.println("Autor no encontrado");
            return;
        }
        System.out.print("Nuevo nombre: ");
        autor.setNom(sc.nextLine());
        autorDAO.update(autor);
        System.out.println("Autor actualizado");
    }

    private static void eliminarAutor() {
        System.out.print("ID del autor a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Autor autor = autorDAO.findById(id);
        if (autor == null) {
            System.out.println("Autor no encontrado.");
            return;
        }
        autorDAO.delete(autor);
    }


    // =========================
    // MENÚ LLIBRE
    // =========================
    private static void menuLlibre() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MENÚ LLIBRE ---");
            System.out.println("1. Añadir Libro");
            System.out.println("2. Listar Libros");
            System.out.println("3. Modificar Libro");
            System.out.println("4. Eliminar Libro");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> crearLlibre();
                case 2 -> listarLlibres();
                case 3 -> modificarLlibre();
                case 4 -> eliminarLlibre();
                case 5 -> back = true;
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private static void crearLlibre() {
        System.out.print("Título del libro: ");
        String titol = sc.nextLine();
        System.out.print("ID del autor: ");
        Long autorId = sc.nextLong();
        sc.nextLine();
        Autor autor = autorDAO.findById(autorId);
        if (autor == null) {
            System.out.println("Autor no encontrado");
            return;
        }
        System.out.print("Número de ejemplares: ");
        int ejemplares = sc.nextInt();
        sc.nextLine();

        Llibre llibre = new Llibre();
        llibre.setTitol(titol);
        llibre.setExemplarsDisponibles(ejemplares);
        llibre.setAutor(autor);
        llibreDAO.save(llibre);
        System.out.println("Libro creado con ID: " + llibre.getId());
    }

    private static void listarLlibres() {
        List<Llibre> llibres = llibreDAO.findAll();
        System.out.println("=== Lista de Libros ===");
        for (Llibre l : llibres) {
            System.out.println("ID: " + l.getId() + " | Título: " + l.getTitol() +
                    " | Autor: " + l.getAutor().getNom() +
                    " | Disponibles: " + l.getExemplarsDisponibles());
        }
    }

    private static void modificarLlibre() {
        System.out.print("ID del libro a modificar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Llibre llibre = llibreDAO.findById(id);
        if (llibre == null) {
            System.out.println("Libro no encontrado");
            return;
        }
        System.out.print("Nuevo título: ");
        llibre.setTitol(sc.nextLine());
        System.out.print("Nuevo número de ejemplares: ");
        llibre.setExemplarsDisponibles(sc.nextInt());
        sc.nextLine();
        llibreDAO.update(llibre);
        System.out.println("Libro actualizado");
    }

    private static void eliminarLlibre() {
        System.out.print("ID del libro a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Llibre llibre = llibreDAO.findById(id);
        if (llibre == null) {
            System.out.println("Libro no encontrado");
            return;
        }
        llibreDAO.delete(llibre);
        System.out.println("Libro eliminado");
    }

    // =========================
    // MENÚ PRESTEC
    // =========================
    private static void menuPrestec() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MENÚ PRÉSTEC ---");
            System.out.println("1. Añadir Préstec");
            System.out.println("2. Listar Préstecs");
            System.out.println("3. Modificar Fecha");
            System.out.println("4. Eliminar Préstec");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> crearPrestec();
                case 2 -> listarPrestecs();
                case 3 -> modificarPrestec();
                case 4 -> eliminarPrestec();
                case 5 -> back = true;
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private static void crearPrestec() {
        System.out.print("ID del libro a prestar: ");
        Long llibreId = sc.nextLong();
        sc.nextLine();
        Llibre llibre = llibreDAO.findById(llibreId);
        if (llibre == null) {
            System.out.println("Libro no encontrado");
            return;
        }

        Prestec prestec = new Prestec();
        prestec.setData(LocalDate.now());
        prestecDAO.crearPrestec(prestec, llibre);
        System.out.println("Préstamo creado con ID: " + prestec.getId());
    }

    private static void listarPrestecs() {
        List<Prestec> prestecs = prestecDAO.findAll();
        System.out.println("=== Lista de Préstecs ===");
        for (Prestec p : prestecs) {
            System.out.print("ID: " + p.getId() + " | Fecha: " + p.getData() + " | Libros: ");
            for (Llibre l : p.getLlibres()) {
                System.out.print(l.getTitol() + " ");
            }
            System.out.println();
        }
    }

    private static void modificarPrestec() {
        System.out.print("ID del prestec a modificar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Prestec prestec = prestecDAO.findById(id);
        if (prestec == null) {
            System.out.println("Prestec no encontrado");
            return;
        }
        System.out.print("Nueva fecha (YYYY-MM-DD): ");
        prestec.setData(LocalDate.parse(sc.nextLine()));
        prestecDAO.update(prestec);
        System.out.println("Prestec actualizado");
    }

    private static void eliminarPrestec() {
        System.out.print("ID del prestec a eliminar: ");
        Long id = sc.nextLong();
        sc.nextLine();
        Prestec prestec = prestecDAO.findById(id);
        if (prestec == null) {
            System.out.println("Prestec no encontrado");
            return;
        }
        prestecDAO.delete(prestec);
        System.out.println("Prestec eliminado");
    }
}
