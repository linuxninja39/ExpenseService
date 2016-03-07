package expense.api.controllers;

import expense.api.dtos.NewExpense;
import expense.api.dtos.UpdateExpense;
import expense.api.entities.DeletedExpense;
import expense.api.entities.Error;
import expense.api.entities.Expense;
import expense.api.entities.ResponseInterface;
import expense.api.repositories.DeletedExpenseRepository;
import expense.api.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaboswell on 3/6/16.
 */
@RestController
public class ExpensesController {
    public static final String ERROR_FIELD_SEPARATOR = ": ";
    public static final String ERROR_ERROR_SEPARATOR = ", ";
    public static final String ERROR_REIMBURSE_MESSAGE = "You can not delete or update an expense that's already been reimbursed";

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    DeletedExpenseRepository deletedExpenseRepository;

    @RequestMapping(path = "/expense", method = RequestMethod.POST)
    public ResponseInterface addExpense(@Validated @RequestBody NewExpense newExpense) {
        Expense expense = new Expense();
        expense.addComment(newExpense.getComment());
        expense.setMerchant(newExpense.getMerchant());
        expense.setTotal(newExpense.getTotal());
        expense.setDatetime(newExpense.getDatetime());
        expense.setStatus(Expense.STATUS_NEW);
        try {
            return expenseRepository.save(expense);
        } catch (ConstraintViolationException e) {
            return new Error(this.generateErrorString(e));
        } catch (Exception e) {
            return  new Error(e.getMessage());
        }
    }

    @RequestMapping(path = "/expense/{id}", method = RequestMethod.GET)
    public ResponseInterface getExpense(@PathVariable String id) {
        try {
            Expense expense = expenseRepository.findById(id);
            if (expense == null) {
                return new Error(String.format("Expense '%s' doesn't exist", id));
            }
            return expense;
        } catch (Exception e) {
            return new Error(e.getMessage());
        }
    }

    @RequestMapping(path = "/expenses", method = RequestMethod.GET)
    public List<? extends ResponseInterface> getExpenses(@RequestParam(required = false) String merchant) {
        try {
            if (merchant != null) {
                return expenseRepository.findByMerchant(merchant);
            }
            return expenseRepository.findAll();
        } catch (Exception e) {
            List<Error> errors = new ArrayList<Error>();
            errors.add(new Error(e.getMessage()));
            return errors;
        }
    }

    @RequestMapping(path = "/expense/{id}", method = RequestMethod.PUT)
    public ResponseInterface updateExpense(@PathVariable String id, @Validated @RequestBody UpdateExpense updateExpense) {
        try {
            Expense expense = expenseRepository.findById(id);
            if (expense.getStatus().equalsIgnoreCase(Expense.STATUS_REIMBURSED)) {
                return new Error(ERROR_REIMBURSE_MESSAGE);
            }
            if (updateExpense.getStatus() != null) expense.setStatus(updateExpense.getStatus());
            if (updateExpense.getMerchant() != null) expense.setMerchant(updateExpense.getMerchant());
            if (updateExpense.getDatetime() != null) expense.setDatetime(updateExpense.getDatetime());
            if (updateExpense.getTotal() != null) expense.setTotal(updateExpense.getTotal());
            if (updateExpense.getComment() != null) expense.addComment(updateExpense.getComment());
            return expenseRepository.save(expense);
        } catch (NullPointerException e) {
            return new Error(String.format("Expense '%s' doesn't exist", id));
        } catch (Exception e) {
            return new Error(e.getMessage());
        }
    }

    @RequestMapping(path = "/expense/{id}", method = RequestMethod.DELETE)
    public ResponseInterface deleteExpense(@PathVariable String id) {
        Expense expense = expenseRepository.findById(id);
        if (expense == null) {
            return new Error(String.format("Expense '%s' doesn't exist", id));
        }
        if (expense.getStatus().equals(Expense.STATUS_REIMBURSED)) {
            return new Error(ExpensesController.ERROR_REIMBURSE_MESSAGE);
        }
        DeletedExpense deletedExpense = new DeletedExpense();
        deletedExpense.setId(expense.getId());
        deletedExpense.setComments(expense.getComments());
        deletedExpense.setDatetime(expense.getDatetime());
        deletedExpense.setMerchant(expense.getMerchant());
        deletedExpense.setTotal(expense.getTotal());
        deletedExpense.setStatus(DeletedExpense.STATUS_DELETED);


        try {
            expenseRepository.delete(expense);
            deletedExpenseRepository.save(deletedExpense);
            return deletedExpense;
        } catch (ConstraintViolationException e) {
            return new Error(this.generateErrorString(e));
        } catch (Exception e) {
            return  new Error(e.getMessage());
        }


    }

    private String generateErrorString(ConstraintViolationException e) {
        ArrayList<String> violationStrings = new ArrayList<String>();
        for (ConstraintViolation violation: e.getConstraintViolations()) {
            violationStrings.add(
                    violation.getPropertyPath()
                    + ERROR_FIELD_SEPARATOR
                    + violation.getMessage()
            );
        }
        return String.join(ERROR_ERROR_SEPARATOR, violationStrings);
    }
}
