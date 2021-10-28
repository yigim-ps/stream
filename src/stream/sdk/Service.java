package stream.sdk;

import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "service")
public class Service implements Serializable {

    private int id;
    private Category category;
    private Type type;
    private String name;
    private String description;
    private String alias;
    private boolean isNew;
    private List<Field> fieldList =
            new ArrayList();
    private List<Currency> currency =
            new ArrayList();
    private int order;
    private boolean active;

    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    @XmlElement(name = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category value) {
        this.category = value;
    }

    @XmlElement(name = "type")
    public Type getType() {
        return type;
    }

    public void setType(Type value) {
        this.type = value;
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

    @XmlElement(name = "isNew")
    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean value) {
        this.isNew = value;
    }

    @XmlElement(name = "fieldList")
    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> value) {
        this.fieldList = value;
    }

    @XmlElement(name = "currency")
    public List<Currency> getCurrencyList() {
        return currency;
    }

    public void setCurrencyList(List<Currency> value) {
        this.currency = value;
    }

    @XmlElement(name = "order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int value) {
        this.order = value;
    }

    @XmlElement(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    @XmlType(namespace = "Service")
    public enum Type {
        Direct,
        Phased
    }

    public Service() {
    }

    public Service(
            int id,
            Category category,
            Type type,
            String name,
            String description,
            String alias,
            boolean n3w,
            int order,
            boolean active
            ) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.name = name;
        this.description = description;
        this.alias = alias;
        this.isNew = n3w;
        this.order = order;
        this.active = active;
    }
}