import java.util.Random;
import java.util.Scanner;

public class Calculator {
    //全局变量定义
    int Result;//变量:记录所出题目的答案
    int Num1, Num2, Num3;//变量：记录随机生成的三个数
    int Time;//变量：记录用户答对题目的次数

    public static void main(String[] args) {
        Calculator t = new Calculator();

        int n;
        Scanner s = new Scanner(System.in);
        System.out.println("请输入题目的数量（1-99）：");
        n = s.nextInt();
        //判断输入的次数是否在范围内
        while(n>99 || n<0){
            System.out.println("请重新输入题目的数量(1-99)：");
            n = s.nextInt();
        }
            t.Motivation(n);
    }
    //四则运算的实现
    public int Add(int n1,int n2){
        return n1 + n2;
    }
    public int Sub(int n1, int n2){
        return n1 - n2;
    }
    public int Mul(int n1, int n2){
        return n1 * n2;
    }
    public int Div(int n1, int n2){
        return n1 / n2;
    }

    public void Motivation(int n){
        int Op1,Op2;
        double x;
        for (int i = 0; i < n; i++) {
            Num1 = new Random().nextInt(99)+1;
            Num2 = new Random().nextInt(99)+1;
            Num3 = new Random().nextInt(99)+1;
            //1=加，2=减，3=乘，4=除
            Op1=new Random().nextInt(4)+1;
            Op2=new Random().nextInt(4)+1;

            //加法
            if(Op1 == 1){
                String str = Num1 + "+" + Num2;
                if(Op2 == 1){
                    Result = Add(Num1, Num2);
                    Result = Add(Result, Num3);
                    str = str + "+" + Num3;/*++*/
                }
                else if(Op2 == 2){
                    Result = Add(Num1, Num2);
                    //确保此Num1+Num2值大于等于Num3，从而保证最终答案不为负数
                    while(Result < Num3){
                        Num3 = new Random().nextInt(99)+1;
                    }
                    Result = Sub(Result, Num3);
                    str = str + "-" + Num3;
                }
                //乘除法优先处理
                else{
                    str = Judge(Op1,Op2);
                }
                Print(str);
            }

            //减法
            else if(Op1 == 2){
//				String str = Num1 + "-" + Num2;
                String str = "";
                if(Op2 == 1){
                    Result = Sub(Num1, Num2);
                    //确保Num1-Num2的绝对值小于等于num3的绝对值，从而保证最终答案不为负数
                    while(Math.abs(Result) > Math.abs(Num3)){
                        Num3 = new Random().nextInt(99)+1;
                    }
                    Result = Add(Result, Num3);
                    str = Num1 + "-" + Num2 + "+" + Num3;
                }
                else if(Op2 == 2){
                    //确保Num1-Num2的值为正数
                    while(Num1 < Num2){//算法不优先，把num1的取值范围缩小了
                        Num1 = new Random().nextInt(99)+1;
                    }
                    Result = Sub(Num1, Num2);
//					str = Num1 + "-" + Num2;
                    //确保Num1- Num2大于等于num3
                    while(Result < Num3){
                        Num3 = new Random().nextInt(99)+1;
                    }
                    Result = Sub(Result, Num3);
                }
                //乘除法优先处理
                else{
                    str = Judge(Op1,Op2);
                }
                Print(str);
            }

            //乘法
            else if(Op1 == 3){
                String str = Num1 + "*" + Num2 +"*" + Num3;
                Result = Mul(Num1, Num2);
                Result = Mul(Result, Num3);
//                str = DivisionOps(Op1,Op2,str);
                Print(str);
            }

            //除法
            else if(Op1 == 4){
                String str = Num1 + "÷" + Num2;
                Result = Div(Num1, Num2);
                str = DivisionOps(Op1,Op2,str);
                Print(str);
            }

            else {
                System.out.println("运算符生成出错，请重启");
            }
        }
        x=100 * Time / n ;
        System.out.println("共生成题目"+n+"题，"+"您答对了"+ Time +"题,准确率为"+x+"%");
    }

    //判断优先级
    public String Judge(int Op1, int Op2){
        String str = "";

        //乘法
        if(Op2 == 3){
            Result = Mul(Num2, Num3);
            str = comPri(Op1,Op2,str);
            str = str + "*" + Num3;
        }

        //除法
        if(Op2 == 4){
            Result = Div(Num2, Num3);
            str = comPri(Op1,Op2,str);
            str = str + "÷" + Num3;
        }

        return str;
    }
    public String comPri(int Op1,int Op2,String str){
        if(Op1 == 1){
            Result = Add(Num1, Result);
            str = Num1 + "+" + Num2;
        }
        if(Op1 == 2){
            //while循环确保最终答案不为负数
            while(Num1 < Result){
                Num2 = new Random().nextInt(99)+1;
                Num3 = new Random().nextInt(99)+1;
                Result = Mul(Num2, Num3);
            }
            Result = Sub(Num1, Result);
            str = Num1 + "-" + Num2;
        }
        return str;
    }

    //对除法运算中的第三个数做判断
    public String DivisionOps(int Op1, int Op2, String str){
        //先除后加
        if(Op2 == 1){
            Result = Add(Result, Num3);
            return str + "+" + Num3;
        }
        //先除后减
        else if(Op2 == 2){
            //确保最终答案不为负数
            while(Result < Num3){
                //重新生成num1，Num2
                Num1 = new Random().nextInt(99)+1;
                Num2 = new Random().nextInt(99)+1;
                Result = Div(Num1, Num2);
                str = Num1 + "÷" + Num2;
            }
            Result = Sub(Result, Num3);
            return str + "-" + Num3;
        }
        //先除后乘
        else if(Op2 == 3){
            Result = Mul(Result, Num3);
            return str + "*" + Num3;
        }
        //两步除法
        else{
            //由于随机生成的数范围是1-99，所以排除了除数为0的可能性
            Result = Div(Result, Num3);
            return str + "÷" + Num3;
        }
    }


    public void Print(String str){

        Scanner s = new Scanner(System.in);
        System.out.print(str + "=");
        int input = s.nextInt();
        if(input == Result){
            Time = Time +1;
            System.out.println("答案正确");
        }
        else {
            System.out.println("答案错误！正确答案为：" + str + "=" + Result);
        }
    }

}

