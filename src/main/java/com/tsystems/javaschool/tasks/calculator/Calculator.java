package com.tsystems.javaschool.tasks.calculator;
import java.util.*;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    public String evaluate(String statement) {
        try{
            String answer = Counting(GetExpression(statement));
            if (answer.endsWith(".0"))
                answer=answer.substring(0,answer.length()-2);
            return answer;

        } catch (Exception e) {
            return null;
        }
    }
    static private String GetExpression(String input)
    {
        String output = "";
        Stack<Character> operStack = new Stack<>();
        for (int i = 0; i < input.length(); i++)
        {
            if (isDelimeter(input.charAt(i)))
                continue;
            if (Character.isDigit(input.charAt(i)))
            {
                while (!isDelimeter(input.charAt(i)) && !isOperator((input.charAt(i))))
                {
                    output += input.charAt(i);
                    i++;
                    if (i == input.length()) break;
                }
                output += " ";
                i--;
            }
            if (isOperator(input.charAt(i)))
            {
                if (input.charAt(i) == '(')
                    operStack.push(input.charAt(i)); //Записываем её в стек
                else if (input.charAt(i) == ')')
                {
                    char s = operStack.pop();
                    while (s != '(')
                    {
                        output += s + ' ';
                        s = operStack.pop();
                    }
                }
                else
                {
                    if (operStack.size() > 0)
                        if (GetPriority(input.charAt(i)) <= GetPriority(operStack.peek())) {
                            output += operStack.pop() + " ";
                        }
                    operStack.push(input.charAt(i));

                }
            }
        }
        while (operStack.size() > 0)
            output += operStack.pop() + " ";

        return output;
    }
    private static boolean isOperator(char c) {
        if ("+-/*^()".indexOf(c) != -1)
            return true;
        return false;
    }
    static private byte GetPriority(char s)
    {
        switch (s)
        {
            case '(': return 3;
            case ')': return 4;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default: return 0;
        }
    }
    static private String Counting(String input) throws Exception
    {
        double result = 0;
        Stack<Double> temp = new Stack<Double>();
        for (int i = 0; i < input.length(); i++)
        {
            if (Character.isDigit(input.charAt(i)))
            {
                String a = "";
                while (!isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i)))
                {
                    a += input.charAt(i);
                    i++;
                    if (i == input.length()) break;
                }
                temp.push(Double.parseDouble(a));
                i--;
            }
            else if (isOperator(input.charAt(i)))
            {
                double a = temp.pop();
                double b = temp.pop();
                switch (input.charAt(i)) //И производим над ними действие, согласно оператору
                {
                    case '+': result = b + a; break;
                    case '-': result = b - a; break;
                    case '*': result = b * a; break;
                    case '/':
                        if(a==0){ throw new UnsupportedOperationException("Division by zero");}
                        else{result = b / a;} break;
                }
                temp.push(result); //Результат вычисления записываем обратно в стек
            }
        }
        return temp.peek().toString();
    }
    static private Boolean isDelimeter (Character c)
    {
        if (" =".indexOf(c) != -1)
            return true;
        return false;
    }
}