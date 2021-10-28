package stream.sdk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "payment")
public class Payment implements Serializable {

    private int id;
    private String reference;
    private Service service;
    private BigDecimal amount;
    private BigDecimal fee;
    private int currency;
    private String iso4217;
    private Date date;
    private String transactionId;
    private String tag;
    private Source origin;
    private List<Detail> detailList =
            new ArrayList();
    private int status;

    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    @XmlElement(name = "reference")
    public String getReference() {
        return reference;
    }

    public void setReference(String value) {
        this.reference = value;
    }

    @XmlElement(name = "service")
    public Service getService() {
        return service;
    }

    public void setService(Service value) {
        this.service = value;
    }

    @XmlElement(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    @XmlElement(name = "fee")
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal value) {
        this.fee = value;
    }

    @XmlElement(name = "currency")
    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int value) {
        this.currency = value;
    }

    @XmlElement(name = "iso4217")
    public String getISO4217() {
        return iso4217;
    }

    public void setISO4217(String value) {
        this.iso4217 = value;
    }

    @XmlElement(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date value) {
        this.date = value;
    }

    @XmlElement(name = "transactionId")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    @XmlElement(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String value) {
        this.tag = value;
    }

    @XmlElement(name = "origin")
    public Source getOrigin() {
        return origin;
    }

    public void setOrigin(Source value) {
        this.origin = value;
    }

    @XmlElement(name = "detail")
    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> value) {
        this.detailList = value;
    }

    @XmlElement(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int value) {
        this.status = value;
    }

    public Payment() {
    }

    public Payment(
            int id,
            String reference,
            Service service,
            BigDecimal amount,
            BigDecimal fee,
            int currency,
            String iso4217,
            Date date,
            String transactionId,
            String tag,
            Source origin,
            int status
            ) {
        this.id = id;
        this.reference = reference;
        this.service = service;
        this.amount = amount;
        this.fee = fee;
        this.currency = currency;
        this.iso4217 = iso4217;
        this.date = date;
        this.transactionId = transactionId;
        this.tag = tag;
        this.origin = origin;
        this.status = status;
    }
}