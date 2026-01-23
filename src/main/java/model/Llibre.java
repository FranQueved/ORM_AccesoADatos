package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "llibres")
public class Llibre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titol;

    private int exemplarsDisponibles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToMany(mappedBy = "llibres", fetch = FetchType.EAGER)
    private List<Prestec> prestecs = new ArrayList<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitol() { return titol; }
    public void setTitol(String titol) { this.titol = titol; }

    public int getExemplarsDisponibles() { return exemplarsDisponibles; }
    public void setExemplarsDisponibles(int exemplarsDisponibles) { this.exemplarsDisponibles = exemplarsDisponibles; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    public List<Prestec> getPrestecs() { return prestecs; }
    public void setPrestecs(List<Prestec> prestecs) { this.prestecs = prestecs; }
}
