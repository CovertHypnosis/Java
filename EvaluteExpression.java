import java.util.Stack;

public class EvaluteExpression {
	public static void main(String[] args) {
		// Check number of arguments passed
		if (args.length != 1) {
			System.out.println("Usage: java EvaluteExpression \"expression\"");
			System.exit(1);
		}
		
		try {
			System.out.println(evaluteExpression(args[0]));
		} catch (Exception ex) {
			System.out.println("Wrong expression: " + args[0]);
		}
	}
	
	/** Evaluate an expression */
	public static int evaluteExpression(String expression) {
		// Create operandStack to store operands
		Stack<Integer> operandStack = new Stack<>();
		
		// Create oeratorStack to store operators
		Stack<Character> operatorStack = new Stack<>();
		
		// Insert blanks around (,),+,-,/ and *
		expression = insertBlanks(expression);
		
		// Extract operands to operators
		String[] tokens = expression.split(" ");
		
		// Phase 1: scan tokens
		for (String token: tokens) {
			if (token.length() == 0)
				continue;
			else if (token.charAt(0) == '+' || token.charAt(0) == '-') {
				// process all operations in the top of the stack
				while (!operatorStack.isEmpty() &&
						(operandStack.peek() == '+' ||
						 operandStack.peek() == '-' ||
						 operandStack.peek() == '*' ||
						 operandStack.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}
				
				// push the new + or - operator in the stack
				operatorStack.push(token.charAt(0));
			} else if (token.charAt(0) == '*' || token.charAt(0) == '/') {
				// process all * and / in the top of the stack
				while (!operatorStack.isEmpty() &&
						(operatorStack.peek() == '*' ||
						 operatorStack.peek() == '/')) {
					processAnOperator(operandStack, operatorStack);
				}
				
				// push the new  * or / operator in the stack
				operatorStack.push(token.charAt(0));
			} else if (token.trim().charAt(0) == '(') {
				operatorStack.push('(');
			} else if (token.trim().charAt(0) == ')') {
				// process all the operators until '('
				while (operatorStack.peek() != '(') {
					processAnOperator(operandStack, operatorStack);
				}
				
				operatorStack.pop();
			} else {
				// push the new number in the stack
				operandStack.push(new Integer(token));
			}
		}
		
		// Phase 2: process all the remaining operators in stack
		while (!operatorStack.isEmpty()) {
			processAnOperator(operandStack, operatorStack);
		}
		
		// return the result
		return operandStack.pop();
	}
	
	public static void processAnOperator(Stack<Integer> operandStack, Stack<Character> operatorStack) {
		char op = operatorStack.pop();
		int op1 = operandStack.pop();
		int op2 = operandStack.pop();
		if (op == '+')
			operandStack.push(op2 + op1);
		else if (op == '-')
			operandStack.push(op2 - op1);
		else if (op == '*')
			operandStack.push(op2 * op1);
		else if (op == '/')
			operandStack.push(op2 / op1);
	}

	public static String insertBlanks(String s) {
		String result = "";
		
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(' || s.charAt(i) == ')' ||
				s.charAt(i) == '+' || s.charAt(i) == '-' ||
				s.charAt(i) == '*' || s.charAt(i) == '/')
				result += " " + s.charAt(i) + " ";
			else
				result += s.charAt(i);
		}
		
		return result;
	}
}
