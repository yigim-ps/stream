package stream.sdk;

import java.math.BigDecimal;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "currency")
public class Currency {

    private String code;
    private String iso4217;
    private String name;
    private String description;
    private BigDecimal min;
    private BigDecimal max;
    private boolean active;

    @XmlAttribute(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    @XmlAttribute(name = "iso4217")
    public String getISO4217() {
        return iso4217;
    }

    public void setISO4217(String value) {
        this.iso4217 = value;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlAttribute(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @XmlAttribute(name = "min")
    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal value) {
        this.min = value;
    }

    @XmlAttribute(name = "max")
    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal value) {
        this.max = value;
    }

    @XmlAttribute(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public Currency() {
    }

    public Currency(
            String code,
            String iso4217,
            String name,
            String description,
            BigDecimal min,
            BigDecimal max,
            boolean active
            ) {
        this.code = code;
        this.iso4217 = iso4217;
        this.name = name;
        this.description = description;
        this.min = min;
        this.max = max;
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("Currency (%s)", iso4217);
    }
}
