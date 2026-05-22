public class Phone {
    // 1. 屬性 (Attributes / Variables)
    String brand;
    String model;
    double price;
    int stock;

    // 2. 建構子 (Constructor) - 用來初始化手機資料
    public Phone(String brand, String model, double price, int stock) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.stock = stock;
    }

    // 3. 方法 (Method) - 顯示手機資訊
    public void displayInfo() {
        System.out.println(brand + " " + model + " - 價格: RM" + price + ", 庫存: " + stock);
    }
}