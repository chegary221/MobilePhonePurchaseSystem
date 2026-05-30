public class MobilePhonePurchaseSystem {
        public static void main(String[] args) {
                System.out.println("手機購買系統啟動成功！");

                // 使用剛才建立的 Phone class 來建立兩隻手機物件 (Objects)
                Phone phone1 = new Phone("F001","Apple", "iPhone 15", 3899.00, 10);
                Phone phone2 = new Phone("F002","Samsung", "Galaxy S24", 3599.00, 10);
                Phone phone3 = new Phone("F003","Honor", "X9b", 2778.00, 5);

                // 呼叫方法顯示手機資訊
                phone1.displayInfo();
                phone2.displayInfo();
                
            CheckoutController checkout = new CheckoutController();
            Customer customer = new Customer("C001", "Gary","gary@gmail.com","123456","0123456789","Penang");
            customer.getCart().addItem(new CartItem(phone1, 2));
            customer.getCart().addItem(new CartItem(phone2, 3));
            customer.getCart().addItem(new CartItem(phone3, 5));
            checkout.buyNow(customer, customer.getCart());}
}
// 【留言区】
// 优先完成自己负责的 USE CASE 的雏形
// 不同人负责的 USE CASE 之间的衔接/建议/建构方向在这里沟通（？ --gary

/* <Checkout相关>
1. add to cart：弄input
2. process payment：由于amount有了在order payment只需要存status和paymethod（？
--gary */

/* <整体系统相关及隐患>
1. order status 似乎需要staff处理 但我发现UC没有manage order之类的 该加还是直接把status删掉？
2. 感觉user的data member可以删掉一两个（？）还有customer和staff那两个id也删掉 user已经有id了 
   代码的部分直接搞成用Cxxx和Sxxx来区分两者
3. generalization目前只有user一种 该往phone上加分支吗 还是有没有其他更优解
*/