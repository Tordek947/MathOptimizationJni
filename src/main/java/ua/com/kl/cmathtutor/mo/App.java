package ua.com.kl.cmathtutor.mo;

public class App {

    public static void main(String[] args) {
	try {
	    // The library should be placed in java.library.path
	    System.loadLibrary("Math");
	} catch (UnsatisfiedLinkError e) {
	    new NativeLoader().loadLibrary("Math");
	}
	int argument = 0;
	try {
	    argument = Integer.valueOf(args[0]);
	} catch (IndexOutOfBoundsException | NumberFormatException e) {
	    System.out.println(
		    "Usage: 'java -jar MathOptimizationJni-jar-with-dependencies.jar value', where 'value' must be an integer"
			    + ", got " + args[0] + " as a value");
	    System.exit(1);
	}
	System.out.println(String.format("Factorial of %s is %s", argument, Math.fact(argument)));
    }

}
