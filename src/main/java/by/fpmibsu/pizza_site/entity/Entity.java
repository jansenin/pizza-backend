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
        if (object != null) {
            if (object != this) {
                if (object.getClass() == getClass() && id != 0) {
                    return id == ((Entity) object).id;
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
