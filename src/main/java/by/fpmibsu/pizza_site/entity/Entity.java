package by.fpmibsu.pizza_site.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        return id.equals(((Entity)object).id);
    }

    @Override
    public int hashCode() {
        return !id.equals(0) ? id.hashCode() : 0;
    }
}
