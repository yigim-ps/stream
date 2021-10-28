package stream.sdk;

import java.io.Serializable;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "detail")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Detail implements Serializable {

    private String name;
    private String label;
    private String value;
    private boolean displayable;

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlAttribute(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String value) {
        this.label = value;
    }

    @XmlValue()
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute(name = "displayable")
    public boolean isDisplayable() {
        return displayable;
    }

    public void setDisplayable(boolean value) {
        this.displayable = value;
    }

    public Detail() {
    }

    public Detail(String name, String value) {
        this.name = name;
        this.value = value;
    }
}