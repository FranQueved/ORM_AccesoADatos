package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prestecs")
public class Prestec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "prestec_llibre",
            joinColumns = @JoinColumn(name = "prestec_id"),
            inverseJoinColumns = @JoinColumn(name = "llibre_id")
    )
    private List<Llibre> llibres = new ArrayList<>();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public List<Llibre> getLlibres() { return llibres; }
    public void setLlibres(List<Llibre> llibres) { this.llibres = llibres; }
}
