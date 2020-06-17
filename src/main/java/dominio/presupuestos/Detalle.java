package dominio.presupuestos;

import java.util.*;
import dominio.compra.*;

public class Detalle {
    List<Item> items = new ArrayList<Item>();

    public double getTotal() {
        return items.stream().mapToDouble(item -> item.get_valor_total()).sum();
    }

    public boolean esIgualA(Detalle detalle) {
        return this.estaIncluidoEn(detalle) && detalle.estaIncluidoEn(this);
    }

    public boolean estaIncluidoEn(Detalle detalle){
        return items.stream().allMatch(unItem -> detalle.tieneItem(unItem));
    }

    public boolean tieneItem(Item otroItem) {
        return items.stream().anyMatch(unItem -> unItem.esIgualA(otroItem));
    }

	public Detalle agregarItem(Item unItem) {
		items.add(unItem);
		return this;
	}
}
