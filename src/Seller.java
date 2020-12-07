public class Seller {
    private int sellerId;
    private String surname;
    private String name;

    public Seller(int sellerId, String surname, String name) {
        this.sellerId = sellerId;
        this.surname = surname;
        this.name = name;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "sellerId=" + sellerId +
                ", surname='" + surname + '\'' +
                ", name='" + name ;
    }
}
