package stream.sdk;

import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "response")
public class Response implements Serializable {

    private String type;
    private String reference;
    private List<Option> optionList     = new ArrayList();
    private List<Payment> paymentList   = new ArrayList();
    private List<Service> serviceList   = new ArrayList();
    private List<Category> categoryList = new ArrayList();
    private String code;
    private String message;
    private String meta;

    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    @XmlElement(name = "option")
    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> value) {
        this.optionList = value;
    }

    @XmlElement(name = "reference")
    public String getReference() {
        return reference;
    }

    public void setReference(String value) {
        this.reference = value;
    }

    @XmlElement(name = "payment")
    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> value) {
        this.paymentList = value;
    }

    @XmlElement(name = "service")
    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> value) {
        this.serviceList = value;
    }

    @XmlElement(name = "category")
    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> value) {
        this.categoryList = value;
    }

    @XmlAttribute(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @XmlAttribute(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    @XmlAttribute(name = "meta")
    public String getMeta() {
        return meta;
    }

    public void setMeta(String value) {
        this.meta = value;
    }

    public Response() {
    }

    public Response(
            String type,
            String reference,
            String code,
            String message,
            String meta
            ) {
        this.type = type;
        this.reference = reference;
        this.code = code;
        this.message = message;
        this.meta = meta;
    }
}