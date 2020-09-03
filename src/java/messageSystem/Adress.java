package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

//identifier for service
public class Adress {
    private static AtomicInteger abonentIdCreator = new AtomicInteger();
    private final int abonentId;

    public Adress(){
        abonentId = abonentIdCreator.getAndIncrement();
    }

    @Override
    public int hashCode() {
        return abonentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Adress)){
            return false;
        }
        Adress anotherAdress = (Adress)obj;
        return anotherAdress.abonentId == this.abonentId;
    }
}
