import java.util.Scanner;

public class Test {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int gte = 0;

        for(int i = 0; i < 10; i++){
            System.out.print("请输入" + (i + 1) + "位学生的成绩:");
            int score = scanner.nextInt();
            sum += score;
            if(score > 80){
                gte++;
            }
        }

        System.out.println("平均分：" + sum/10);
        System.out.println("80分以上的人数是：" + gte);

    }
}
