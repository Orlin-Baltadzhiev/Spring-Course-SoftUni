package spirngdataadvancequeries.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spirngdataadvancequeries.entity.DataAccessObject.IngredientRepository;
import spirngdataadvancequeries.entity.DataAccessObject.LabelRepository;
import spirngdataadvancequeries.entity.DataAccessObject.ShampooRepository;
import spirngdataadvancequeries.entity.Ingredient;
import spirngdataadvancequeries.entity.Shampoo;
import spirngdataadvancequeries.entity.utils.PrintUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static spirngdataadvancequeries.entity.Size.MEDIUM;

@Component
public class AppInitializer implements CommandLineRunner {
    private final ShampooRepository shampooRepository;
    private final LabelRepository labelRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public AppInitializer(ShampooRepository shampooRepository, LabelRepository labelRepository, IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.labelRepository = labelRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //Fetch Shampoos by Size
//     shampooRepository.findAllBySizeOrderById(MEDIUM)
//             .forEach(PrintUtil::printShampoo);
//
//       System.out.print("-".repeat(150) + "\n" );
//
//       shampooRepository.findAllBySizeOrLabelOrderByPriceDesc(MEDIUM, 10).forEach(PrintUtil::printShampoo);
//
      //  System.out.print("-".repeat(150) + "\n" );
//       //Fetch by ingredients in List
//       shampooRepository.findWithIngredientsIn(Set.of("Berry","Mineral-Collagen")).forEach(PrintUtil::printShampoo);

        //Fetch Shampoos bu Price greater than or equal
       //shampooRepository.findByPriceGreaterThanEqual(5).forEach(PrintUtil::printShampoo);

       //Fetch Shampoos by price between min and max;
       // shampooRepository.findByPriceBetween(5,8).forEach(PrintUtil::printShampoo);

    //    ingredientRepository.findByNameIn(Set.of("Lavender","Herbs","Apple")).forEach(PrintUtil::printIngredients);


        // DELETE OPERATION на метода tрябва да добавим  @Transactional

//        String nameToDelete = "Macadamia Oil";
//        Ingredient ingredientToDelete =ingredientRepository.findByName(nameToDelete).orElse(null); // tuka trqbva da e Optional
//        List<Shampoo> shampoosByIngredient= shampooRepository.findByIngredient(ingredientToDelete);
//        shampoosByIngredient.forEach(PrintUtil::printShampoo);
//        shampoosByIngredient.forEach(shampoo -> shampoo.getIngredients().remove(ingredientToDelete));
//        System.out.printf("Number of deleted ingredients with name = '%s' is: %d",
//                nameToDelete, ingredientRepository.deleteAllByName(nameToDelete)); //  here we also put @Transactional
//        System.out.println("-".repeat(180) + "\n");
//        shampooRepository.findAll().forEach(PrintUtil::printShampoo);

        //Increase price of ingredients in list by percentage UPDATE
        ingredientRepository.findAll().forEach(PrintUtil::printIngredients);
        System.out.println("-".repeat(180) + "\n");
        ingredientRepository.updatePriceOfIngredientsInList(Set.of("Lavender","Herbs","Apple"),10);
       ingredientRepository.findAll().forEach(PrintUtil::printIngredients);


    }


}
