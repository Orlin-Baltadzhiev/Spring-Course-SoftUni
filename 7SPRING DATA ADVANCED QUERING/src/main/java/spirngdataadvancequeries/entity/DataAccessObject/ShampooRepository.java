package spirngdataadvancequeries.entity.DataAccessObject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spirngdataadvancequeries.entity.Ingredient;
import spirngdataadvancequeries.entity.Shampoo;
import spirngdataadvancequeries.entity.Size;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySizeOrderById(Size size);
//вместо JOIN @Query("SELECT s FROM Shampoo s, IN(s.ingredients) i WHERE i.name IN :ingredient_names")
    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN :ingredient_names")
    List<Shampoo> findWithIngredientsIn(@Param("ingredient_names")Iterable<String> ingredient_names);

    List<Shampoo>findAllBySizeOrLabelOrderByPriceDesc(Size medium, int s);

    List<Shampoo> findByPriceGreaterThanEqual(double price);

    List<Shampoo> findByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT s FROM Shampoo s WHERE s.ingredients.size <= :count")
    List<Shampoo> findByCountOfIngredientsLowerThan(@Param("count") int maxCount);

    @Query("SELECT s FROM Shampoo s, IN(s.ingredients) i WHERE i =:ingredient")
    List<Shampoo> findByIngredient(Ingredient ingredient);
}
