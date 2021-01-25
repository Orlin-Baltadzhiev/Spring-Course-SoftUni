package softuni.cardealer.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.cardealer.domain.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

   @Query("SELECT count(p) FROM Part p where p.supplier.id = ?1 GROUP BY p.supplier ")
   int findParts(long id);
}
