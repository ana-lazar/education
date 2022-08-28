package ro.ubb.pm.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.pm.model.RememberMeToken;

@Repository
public interface RememberMeTokensRepository extends JpaRepository<RememberMeToken, Integer> {
}
