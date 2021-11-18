package br.com.wallet.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class BusinessException extends RuntimeException {
   
	private static final long serialVersionUID = -8006760629397176660L;
	private String resourceName;
    private String fieldName;
    private Object fieldValue;
    private String message;

	public BusinessException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s Business Exception %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;        
    }
	
	public BusinessException( String resourceName, String fieldName, Object fieldValue, String message) {
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;  
        this.message = message;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}