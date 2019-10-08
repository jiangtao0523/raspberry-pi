import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("****************加减乘除运算****************");
        System.out.print("请输入第一个操作数：");
        int first = scanner.nextInt();
        System.out.print("请输入第二个操作数：");
        int second = scanner.nextInt();
        System.out.print("输入运算符：");
        String operation = scanner.next();
        switch (operation){
            case "+" :
                int sum = first + second;
                System.out.println("显示结果为：" + first + "+" + second + "=" + sum);
                break;
            case "-" :
                int sub = first - second;
                System.out.println("显示结果为：" + first + "-" + second + "=" + sub);
                break;
            case "*" :
                int mul = first * second;
                System.out.println("显示结果为：" + first + "*" + second + "=" + mul);
                break;
            case "/" :
                int div = first / second;
                System.out.println("显示结果为：" + first + "/" + second + "=" + div);
                break;
        }
    }
}
