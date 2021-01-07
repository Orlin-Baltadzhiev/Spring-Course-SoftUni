package spirngdataadvancequeries.entity.DataAccessObject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spirngdataadvancequeries.entity.Shampoo;
@Repository
public interface LabelRepository extends JpaRepository<Shampoo, Long> {


}
