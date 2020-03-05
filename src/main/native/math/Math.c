

 extern int fact(int n) {
	 if (n < 0) {
		 return -1;
	 }
	 int factorial = 1;
	 for (int i = 2; i <= n; i++) {
		 factorial *= i;
	 }
	 return factorial;
 }
