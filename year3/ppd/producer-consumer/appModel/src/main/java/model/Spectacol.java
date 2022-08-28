package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spectacole")
public class Spectacol implements HasId<Long> {
    @Id
    @Column(name = "spectacol_id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long spectacolId;

    private Integer locuriRamase;

//    @Temporal(TemporalType.DATE)
    private LocalDate date;

    private String titlu;

    private Double pretBilet;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "spectacol_locuri",
            joinColumns = @JoinColumn(name = "fk_spectacol")
    )
    @OrderColumn(name = "idx_loc")
    private List<Integer> locuriVandute;

    private Double total;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_spectacol")
//            (mappedBy = "spectacol")
    private List<Vanzare> listaVanzari = new ArrayList<>();

    public Spectacol() {
    }

    @Override
    public Long getId() {
        return spectacolId;
    }

    @Override
    public void setId(Long aLong) {
        spectacolId = aLong;
    }

    public Integer getLocuriRamase() {
        return locuriRamase;
    }

    public void setLocuriRamase(Integer locuriRamase) {
        this.locuriRamase = locuriRamase;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public Double getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(Double pretBilet) {
        this.pretBilet = pretBilet;
    }

    public List<Integer> getLocuriVandute() {
        return locuriVandute;
    }

    public void setLocuriVandute(List<Integer> locuriVandute) {
        this.locuriVandute = locuriVandute;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Vanzare> getListaVanzari() {
        return listaVanzari;
    }

    public void setListaVanzari(List<Vanzare> listaVanzari) {
        this.listaVanzari = listaVanzari;
    }

    public void addVanzare(Vanzare vanzare) {
        this.listaVanzari.add(vanzare);
    }
}
