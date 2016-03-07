package expense.api.entities;

/**
 * Created by jaboswell on 3/6/16.
 */
public class Error implements ResponseInterface {
    private String error;

    public Error() {}
    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
