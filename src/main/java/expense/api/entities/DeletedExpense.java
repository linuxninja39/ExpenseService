package expense.api.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jaboswell on 3/7/16.
 */
public class DeletedExpense implements ResponseInterface {
    public static final String STATUS_DELETED = "deleted";
    public static final String STATUS_VALIDATE_PATTERN = STATUS_DELETED;

    @Id
    private String id;
    @NotNull
    private String merchant;
    @NotNull
    @Min(0)
    private Double total;
    @NotNull
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date datetime;
    @NotNull
    @Pattern(regexp = STATUS_VALIDATE_PATTERN, flags = {Pattern.Flag.CASE_INSENSITIVE})
    protected String status;
    private List<String> comments = new ArrayList<String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        this.getComments().add(comment);
    }
}
