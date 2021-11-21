package be.dekleinekobini.tornapi.models.user;

import be.dekleinekobini.tornapi.models.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Objects;

public class Ammo extends Model {

    @JsonProperty("ammoID")
    private long ammoId;
    @JsonProperty("typeID")
    private long typeId;
    private String size;
    private String type;
    private boolean equipped;

    public static List<Ammo> of(JsonNode json) {
        return OBJECT_MAPPER.convertValue(json.get("ammo"), new TypeReference<>() {
        });
    }

    public long getAmmoId() {
        return ammoId;
    }

    public void setAmmoId(long ammoId) {
        this.ammoId = ammoId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ammo ammo = (Ammo) o;
        return ammoId == ammo.ammoId && typeId == ammo.typeId && equipped == ammo.equipped && Objects.equals(size, ammo.size) && Objects.equals(type, ammo.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ammoId, typeId, size, type, equipped);
    }

}
