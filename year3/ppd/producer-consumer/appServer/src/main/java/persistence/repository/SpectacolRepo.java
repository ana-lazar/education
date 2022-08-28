package persistence.repository;

import model.Spectacol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectacolRepo extends JpaRepository<Spectacol, Long> {
}
