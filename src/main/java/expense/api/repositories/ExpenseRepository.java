package expense.api.repositories;

import expense.api.entities.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by jaboswell on 3/6/16.
 */
public interface ExpenseRepository extends MongoRepository<Expense, Long> {
    public Expense findById(String id);
    public List<Expense> findByStatus(String status);
    public List<Expense> findByMerchant(String merchant);
}
