package softuni.cardealer.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import softuni.cardealer.domain.entities.Supplier;

import java.util.Set;

@Repository
public interface SupplierRepository  extends JpaRepository<Supplier, Long> {

    Set<Supplier> findAllByImporterIsFalse();

}
