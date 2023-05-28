package by.fpmibsu.pizza_site.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return id == ((Entity)object).id;
    }

    @Override
    public int hashCode() {
        return id != 0 ? Integer.hashCode(id) : 0;
    }
}
