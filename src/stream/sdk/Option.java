package stream.sdk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "option")
public class Option implements Serializable {

    private String id;
    private String name;
    private BigDecimal amount;
    private BigDecimal min;
    private BigDecimal max;
    private boolean fixed;
    private int currency;
    private String iso4217;
    private List<BigDecimal> fixedAmountList =
            new ArrayList();
    private String color;
    private List<Detail> detailList;

    @XmlElement(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlElement(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    @XmlElement(name = "min")
    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal value) {
        this.min = value;
    }

    @XmlElement(name = "max")
    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal value) {
        this.max = value;
    }

    @XmlElement(name = "fixed")
    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean value) {
        this.fixed = value;
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

    @XmlElement(name = "fixedAmountList")
    public List<BigDecimal> getFixedAmountList() {
        return fixedAmountList;
    }

    public void setFixedAmountList(List<BigDecimal> value) {
        this.fixedAmountList = value;
    }

    @XmlElement(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String value) {
        this.color = value;
    }

    @XmlElement(name = "detail")
    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> value) {
        this.detailList = value;
    }

    public Option() {
    }

    public Option(
            String id,
            String name,
            BigDecimal amount,
            BigDecimal min,
            BigDecimal max,
            boolean fixed,
            int currency,
            String iso4217,
            String color,
            List<Detail> detailList
            ) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.min = min;
        this.max = max;
        this.fixed = fixed;
        this.currency = currency;
        this.iso4217 = iso4217;
        this.color = color;
        this.detailList = detailList;
    }
}
