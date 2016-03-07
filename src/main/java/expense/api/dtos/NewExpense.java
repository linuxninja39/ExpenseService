package expense.api.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by jaboswell on 3/7/16.
 */
public class NewExpense {
    @NotNull
    private String merchant;
    @NotNull
    @Min(0)
    private Double total;
    @NotNull
    @Past
    private Date datetime;
    private String comment;

    public NewExpense() {}
    public NewExpense(String merchant, Double total, Date datetime, String comment) {
        this.merchant = merchant;
        this.total = total;
        this.datetime = datetime;
        this.comment = comment;
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
}
