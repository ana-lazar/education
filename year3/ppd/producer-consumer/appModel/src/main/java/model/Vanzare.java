package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vanzari")
public class Vanzare implements HasId<Long> {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

//    @Temporal(TemporalType.DATE)
    private LocalDate dataVanzare;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "vanzare_locuri",
            joinColumns = @JoinColumn(name = "fk_vanzare")
    )
    @OrderColumn(name = "idx_loc")
    private List<Integer> locuriVandute = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "spectacol_id")
//    private Spectacol spectacol;

    public Vanzare() {}

    public Vanzare(LocalDate dataVanzare) {
        this.dataVanzare = dataVanzare;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long aLong) {
        this.id = aLong;
    }

    public LocalDate getDataVanzare() {
        return dataVanzare;
    }

    public void setDataVanzare(LocalDate dataVanzare) {
        this.dataVanzare = dataVanzare;
    }

    public List<Integer> getLocuriVandute() {
        return locuriVandute;
    }

    public void setLocuriVandute(List<Integer> locuriVandute) {
        this.locuriVandute = locuriVandute;
    }
}
