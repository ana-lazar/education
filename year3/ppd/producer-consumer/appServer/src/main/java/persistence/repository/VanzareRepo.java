package persistence.repository;

import model.Vanzare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VanzareRepo extends JpaRepository<Vanzare, Long> {
}
