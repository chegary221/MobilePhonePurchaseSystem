public class MobilePhonePurchaseSystem {
        public static void main(String[] args) {
                System.out.println("手機購買系統啟動成功！");

                // 使用剛才建立的 Phone class 來建立兩隻手機物件 (Objects)
                Phone phone1 = new Phone("F001","Apple", "iPhone 15", 3899.00, 10,"Stock Available");
                Phone phone2 = new Phone("F002","Samsung", "Galaxy S24", 3599.00, 5,"Stock Available");

                // 呼叫方法顯示手機資訊
                phone1.displayInfo();
                phone2.displayInfo();
                
            CheckoutController checkout = new CheckoutController();
            Customer customer = new Customer("C001", "Gary","gary@gmail.com","123456","0123456789","Penang","ACC001");
            Phone phone = new Phone("F001","Apple", "iPhone 15", 3899.00, 10,"Stock Available");
            Cart cart = new Cart(10);
            cart.addItem(new CartItem(phone, 2));
            checkout.buyNow(customer, phone, cart, 2);}
}
