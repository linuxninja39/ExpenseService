package expense.api.dtos;

import expense.api.entities.Expense;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by jaboswell on 3/7/16.
 */
public class UpdateExpense {
    private String merchant;
    @Min(0)
    private Double total;
    @Past
    private Date datetime;
    private String comment;
    @Pattern(regexp = Expense.STATUS_VALIDATE_PATTERN, flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String status;

    public UpdateExpense() {}
    public UpdateExpense(String merchant, Double total, Date datetime, String status, String comment) {
        this.merchant = merchant;
        this.total = total;
        this.datetime = datetime;
        this.comment = comment;
        this.status = status;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
