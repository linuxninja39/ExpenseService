package expense.api.controllers;

import expense.api.entities.DeletedExpense;
import expense.api.entities.Error;
import expense.api.entities.Expense;
import expense.api.repositories.DeletedExpenseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by jaboswell on 3/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExpensesControllerDeleteUnitTest extends ExpensesControllerUnitTest {

    @Mock
    DeletedExpenseRepository deletedExpenseRepository;

    @Test
    public void successfulDelete() {
        String id = "one";
        Expense toBeDeleted = new Expense();
        toBeDeleted.setId(id);
        toBeDeleted.setStatus(Expense.STATUS_NEW);
        Mockito.when(this.expenseRepository.findById(id)).thenReturn(toBeDeleted);

        DeletedExpense deletedExpense = (DeletedExpense) this.expensesController.deleteExpense(id);

        Assert.assertEquals(id, deletedExpense.getId());
        Assert.assertEquals(DeletedExpense.STATUS_DELETED, deletedExpense.getStatus());
    }

    @Test
    public void failToDeleteReimbursed() {
        String id = "one";
        Expense toBeDeleted = new Expense();
        toBeDeleted.setId(id);
        toBeDeleted.setStatus(Expense.STATUS_REIMBURSED);
        Mockito.when(this.expenseRepository.findById(id)).thenReturn(toBeDeleted);

        Error error = (Error) this.expensesController.deleteExpense(id);

        Assert.assertNotNull(error.getError());
        Assert.assertEquals(ExpensesController.ERROR_REIMBURSE_MESSAGE, error.getError());
    }
}
