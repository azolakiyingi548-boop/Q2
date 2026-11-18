public class ProductCalculations implements IProductCalculations{
    private double price;

    public ProductCalculations(double price) {
        this.price = price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double GetVat() {
        return price * 0.15;
    }

    @Override
    public double GetCommission() {
        return price * 0.05;
    }

    @Override
    public double GetTotal() {
        return price + GetVat() + GetCommission();

    }
}
