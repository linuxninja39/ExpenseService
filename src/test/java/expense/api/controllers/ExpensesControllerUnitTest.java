package expense.api.controllers;

import expense.api.repositories.ExpenseRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Created by jaboswell on 3/7/16.
 */
public class ExpensesControllerUnitTest {
    public static final String ERROR_FIELD = "merchant";
    public static final String ERROR_MESSAGE = "may not be null";

    @Mock
    ExpenseRepository expenseRepository;

    @InjectMocks
    ExpensesController expensesController;

}
