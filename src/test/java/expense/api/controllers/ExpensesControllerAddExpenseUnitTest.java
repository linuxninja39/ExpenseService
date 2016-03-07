package expense.api.controllers;

import expense.api.dtos.NewExpense;
import expense.api.entities.Error;
import expense.api.entities.Expense;
import expense.api.entities.ResponseInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jaboswell on 3/6/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExpensesControllerAddExpenseUnitTest extends ExpensesControllerUnitTest {
    @Test
    public void successfulInsertWithoutComment() {
        String merchant = "2112";
        Double total = 2.22;
        Date now = new Date();

        NewExpense submittedExpense = this.setupExpense(merchant, total, now);

        Expense returnExpense = new Expense();
        returnExpense.setMerchant(merchant);
        returnExpense.setTotal(total);
        returnExpense.setDatetime(now);
        returnExpense.setStatus("new");
        returnExpense.setId("entre nous");

        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(returnExpense);

        ResponseInterface returnedExpense = expensesController.addExpense(submittedExpense);
        Assert.assertEquals(Expense.class, returnedExpense.getClass());
        Expense expense = (Expense) returnedExpense;

        this.assertNewExpense(submittedExpense, expense);

    }

    @Test
    public void successfulInsertWithComment() {
        String merchant = "Kid Gloves";
        Double total = 10010010.01;
        Date now = new Date();
        List<String> comments = new ArrayList<String>();
        String comment = "Tom Sawyer";
        comments.add(comment);

        NewExpense submittedExpense = this.setupExpense(merchant, total, now);
        submittedExpense.setComment(comment);

        Expense returnExpense = new Expense();
        returnExpense.setMerchant(merchant);
        returnExpense.setTotal(total);
        returnExpense.setDatetime(now);
        returnExpense.setComments(comments);
        returnExpense.setStatus("new");
        returnExpense.setId("Power Windows");
        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(returnExpense);

        ResponseInterface returnedExpense = expensesController.addExpense(submittedExpense);
        Assert.assertEquals(Expense.class, returnedExpense.getClass());
        Expense expense = (Expense) returnedExpense;

        this.assertNewExpense(submittedExpense, expense);
        Assert.assertEquals(submittedExpense.getComment(), expense.getComments().get(0));

    }


    private NewExpense setupExpense(String merchant, Double total, Date dateTime) {
        NewExpense insertExpense = new NewExpense();
        insertExpense.setDatetime(dateTime);
        insertExpense.setTotal(total);
        insertExpense.setMerchant(merchant);

        return insertExpense;
    }

    private void assertNewExpense(NewExpense controlExpense, Expense expense) {
        Assert.assertEquals("Merchant", controlExpense.getMerchant(), expense.getMerchant());
        Assert.assertEquals("Total", controlExpense.getTotal(), expense.getTotal());
        Assert.assertEquals("DateTime", controlExpense.getDatetime(), expense.getDatetime());
        Assert.assertEquals("Status", "new", expense.getStatus());
        Assert.assertNotNull("Id", expense.getId());
    }

    @Test
    public void missingData() {
        NewExpense insertExpense = new NewExpense();

        ConstraintViolation v = new TestConstraintViolation();
        HashSet<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
        violations.add(v);
        ConstraintViolationException e = new ConstraintViolationException(violations);
        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenThrow(e);

        ResponseInterface returnedError = expensesController.addExpense(insertExpense);
        Assert.assertEquals(Error.class, returnedError.getClass());
        Error error = (Error) returnedError;

        Assert.assertNotNull(error.getError());
        Assert.assertEquals(ERROR_FIELD + ExpensesController.ERROR_FIELD_SEPARATOR + ERROR_MESSAGE, error.getError());
    }

    @Test
    public void otherException() {
        String msg = "Tai Shan";
        NewExpense insertExpense = new NewExpense();
        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenThrow(new RuntimeException(msg));

        ResponseInterface returnedError = expensesController.addExpense(insertExpense);
        Assert.assertEquals(Error.class, returnedError.getClass());
        Error error = (Error) returnedError;

        Assert.assertNotNull(error.getError());
        Assert.assertEquals(msg, error.getError());
    }

    private class TestConstraintViolation implements ConstraintViolation<String> {

        @Override
        public String getMessage() {
            return ERROR_MESSAGE;
        }

        @Override
        public String getMessageTemplate() {
            return null;
        }

        @Override
        public String getRootBean() {
            return null;
        }

        @Override
        public Class<String> getRootBeanClass() {
            return null;
        }

        @Override
        public Object getLeafBean() {
            return null;
        }

        @Override
        public Object[] getExecutableParameters() {
            return new Object[0];
        }

        @Override
        public Object getExecutableReturnValue() {
            return null;
        }

        @Override
        public Path getPropertyPath() {
            return new Path() {
                @Override
                public Iterator<Node> iterator() {
                    return null;
                }
                @Override
                public String toString() {
                    return ERROR_FIELD;
                }
            };
        }

        @Override
        public Object getInvalidValue() {
            return null;
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public <U> U unwrap(Class<U> type) {
            return null;
        }
    }
}
