public class Program {
    public static void main(String[] args) {
        System.out.println("Hello");

        Converter converter = new Converter();
        var res1 = converter.HexToBytes("74657374");
        System.out.println(res1);
    }
}
