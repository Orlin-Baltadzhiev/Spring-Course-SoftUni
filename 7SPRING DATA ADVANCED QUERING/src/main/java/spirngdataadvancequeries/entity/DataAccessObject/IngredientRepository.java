package spirngdataadvancequeries.entity.DataAccessObject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spirngdataadvancequeries.entity.Ingredient;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient>findByNameIn(Iterable<String> names);

    Optional<Ingredient> findByName(String nameToDelete);
    @Transactional// ne e zaduljitelno
  int deleteAllByName(String nameToDelete);


    @Transactional
    @Modifying
    @Query("UPDATE Ingredient i SET i.price = i.price *  :percentage WHERE i.name IN :names")
    void updatePriceOfIngredientsInList(@Param("names") Iterable<String> names,
                                      @Param("percentage") double percentage);
}
