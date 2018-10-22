public class Person {
    public boolean ifGo;
    public int id;
    public int hotelId;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Person(int id) {
        this.id = id;
    }

    public Person(boolean ifGo, int id) {
        this.ifGo = ifGo;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIfGo() {
        return ifGo;
    }

    public void setIfGo(boolean ifGo) {
        this.ifGo = ifGo;
    }
}
