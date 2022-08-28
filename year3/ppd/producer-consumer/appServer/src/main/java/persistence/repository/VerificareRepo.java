package persistence.repository;

import model.Verificare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificareRepo extends JpaRepository<Verificare, Long> {
}
