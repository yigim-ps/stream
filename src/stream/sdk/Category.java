package stream.sdk;

import java.io.Serializable;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "category")
public class Category implements Serializable {

    private int id;
    private String name;
    private String description;
    private String alias;
    private int order;
    private boolean empty;
    private boolean active;

    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @XmlElement(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    @XmlElement(name = "order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int value) {
        this.order = value;
    }

    @XmlElement(name = "empty")
    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean value) {
        this.empty = value;
    }

    @XmlElement(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public Category() {
    }

    public Category(
            int id,
            String name,
            String description,
            String alias,
            int order,
            boolean empty,
            boolean active
            ) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.alias = alias;
        this.order = order;
        this.empty = empty;
        this.active = active;
    }
}