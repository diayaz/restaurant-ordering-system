package restaurant.observer;

/** Life cycle of an order. Author: Dias (Student 2) */
public enum OrderStatus {
    PLACED, COOKING, READY, SERVED, PAID, CANCELLED;

    /** Next status in the normal kitchen flow (PLACED -> COOKING -> READY -> SERVED). */
    public OrderStatus next() {
        switch (this) {
            case PLACED:  return COOKING;
            case COOKING: return READY;
            case READY:   return SERVED;
            default:      return this;
        }
    }
}
