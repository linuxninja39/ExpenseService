package expense.api.repositories;

import expense.api.entities.DeletedExpense;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jaboswell on 3/7/16.
 */
public interface DeletedExpenseRepository extends MongoRepository<DeletedExpense, String> {
}
