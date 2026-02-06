package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autors")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement de autores
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//enlaza autor con libros (mapped by)
    private List<Llibre> llibres = new ArrayList<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public List<Llibre> getLlibres() { return llibres; }
    public void setLlibres(List<Llibre> llibres) { this.llibres = llibres; }
}
