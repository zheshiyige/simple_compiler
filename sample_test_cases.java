// simple numeric function

 int nums() {
   int x, y, p, q, z;
	x = 5;
	y = x*6 + 1;
	z = y*2 - x/1;
	p = ((x + 1) * (y - 2)) / (y - 2);
	q = p * p;
   return q;
}

 // must give max2 before max4

 int max2(int x, int y) {
    if (x > y)
       return x;
    else  
	    return y;
 }

 int max4(int w, int x, int y, int z) {
    int t1, t2;
    t1 = max2(w,x);
    t2 = max2(y,z);
 	 return max2(t1,t2);
 }

 
// factorial function

int fact(int n) {
   int i, f;
   i = 1;
   f = 1;
   while (i < n) {
     i = i + 1;
     f= f * i;
   }
   return f;
}

 // recursive gcd

 int rgcd(int x, int y) {
    if (x > y)  
		return rgcd(x-y, y); 
    if (x < y)  
		return rgcd(x, y-x); 
    return x;
 }


// McCarthy 91 function

 int f91(int n) {
	 int ans, temp;
	 if (n > 100)
        ans = n-10; 
    else {
	     temp = f91(n+11);
        ans = f91(temp); 
    }  
    return ans;
  }

end

Lexical analyzer does not read past 'end'.

 
    
    
