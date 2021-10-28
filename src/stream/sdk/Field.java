package stream.sdk;

import java.io.Serializable;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "field")
public class Field implements Serializable {

    private Type type;
    private String name;
    private String label;
    private String description;
    private String hint;
    private int capacity;
    private String pattern;
    private boolean editable;
    private int order;
    private boolean show;
    private boolean active;

    @XmlAttribute(name = "type")
    public Type getType() {
        return type;
    }

    public void setType(Type value) {
        this.type = value;
    }

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

    @XmlAttribute(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @XmlAttribute(name = "hint")
    public String getHint() {
        return hint;
    }

    public void setHint(String value) {
        this.hint = value;
    }

    @XmlAttribute(name = "capacity")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int value) {
        this.capacity = value;
    }

    @XmlAttribute(name = "pattern")
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String value) {
        this.pattern = value;
    }

    @XmlAttribute(name = "editable")
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean value) {
        this.editable = value;
    }

    @XmlAttribute(name = "order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int value) {
        this.order = value;
    }

    @XmlAttribute(name = "show")
    public boolean isShow() {
        return show;
    }

    public void setShow(boolean value) {
        this.show = value;
    }

    @XmlAttribute(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public Field() {
    }

    public Field(
            Type type,
            String name,
            String label,
            String description,
            String hint,
            int capacity,
            String pattern,
            boolean editable,
            int order,
            boolean show,
            boolean active
            ) {
        this.type = type;
        this.name = name;
        this.label = label;
        this.description = description;
        this.hint = hint;
        this.capacity = capacity;
        this.pattern = pattern;
        this.editable = editable;
        this.order = order;
        this.show = show;
        this.active = active;
    }

    @XmlType(namespace = "Field")
    public enum Type {
        DateTime,
        Text,
        Password,
        URL,
        Email,
        Numeric,
        Checkbox,
        Radio,
        MSISDN,
        Amount,
        List,
        Hidden
    }

    @Override
    public String toString() {
        return String.format(
                "Field (Type: %s, Name: %s, Label: %s, Description: %s, Hint: %s, Capacity: %s, Pattern: %s, Editable: %s, Order: %s, Show: %s, Active: %s)",
                type,
                name,
                label,
                description,
                hint,
                capacity,
                pattern,
                editable,
                order,
                show,
                active
                );
    }
}