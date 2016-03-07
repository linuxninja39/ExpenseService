package expense.api.controllers;

import expense.api.dtos.UpdateExpense;
import expense.api.entities.Error;
import expense.api.entities.Expense;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

/**
 * Created by jaboswell on 3/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExpensesControllerUpdateExpenseUnitTest extends ExpensesControllerUnitTest {
    @Test
    public void successfulUpdate() {
        String merchant = "Xanadu";
        Double total = 743.33;
        String status = "reimbursed";
        Date dateTime = new Date();
        String comment = "another one";
        String id = "one";
        UpdateExpense updateExpense = new UpdateExpense(merchant, total, dateTime, status, comment);

        Expense returnedExpense = new Expense();
        returnedExpense.setId(id);
        returnedExpense.setDatetime(dateTime);
        returnedExpense.addComment(comment);
        returnedExpense.setStatus("new");
        returnedExpense.setMerchant(merchant);

        Mockito.when(this.expenseRepository.findById(id)).thenReturn(returnedExpense);
        Mockito.when(this.expenseRepository.save(Mockito.any(Expense.class))).thenReturn(returnedExpense);

        Expense expense = (Expense) this.expensesController.updateExpense(id, updateExpense);

        Assert.assertEquals(merchant, expense.getMerchant());
        Assert.assertEquals(total, expense.getTotal());
        Assert.assertEquals(status, expense.getStatus());
        Assert.assertEquals(dateTime, expense.getDatetime());
        Assert.assertEquals(comment, expense.getComments().get(0));
    }

    @Test
    public void attemptedUpdateReimbursedExpense() {
        String merchant = "Xanadu";
        Double total = 743.33;
        String status = "reimbursed";
        Date dateTime = new Date();
        String comment = "another one";
        String id = "one";
        UpdateExpense updateExpense = new UpdateExpense(merchant, total, dateTime, status, comment);

        Expense returnedExpense = new Expense();
        returnedExpense.setId(id);
        returnedExpense.setDatetime(dateTime);
        returnedExpense.addComment(comment);
        returnedExpense.setStatus(status);
        returnedExpense.setMerchant(merchant);

        Mockito.when(this.expenseRepository.findById(id)).thenReturn(returnedExpense);

        Error error = (Error) this.expensesController.updateExpense(id, updateExpense);

        Assert.assertNotNull(error.getError());
        Assert.assertEquals(ExpensesController.ERROR_REIMBURSE_MESSAGE, error.getError());
    }
}
