#include <stdio.h>
#include <stdlib.h>

int gcd(int m, int n)
{
    int r;
    if((m == 0) || (n == 0))
        return 0;
    else if((m < 0) || (n < 0))
        return -1;
 
    do
    {
        r = m % n;
        if(r == 0)
            break;
        m = n;
        n = r;
    }
    while(1);
 
    return n;
}

int main()
{
    int T,N;
    int i,j,k,count,L,num;
    int *a;
    scanf("%d",&T);
    while(T>0)
    {
    	scanf("%d",&N);
    	count = 0;
		/* When m and n are any two positive integers (m < n):
			a = n^2 - m^2
			b = 2*n*m
			c = n^2 + m^2
			Then a, b, and c form a Pythagorean Triple.
		*/
    	for(i=1;i<=(int)(sqrt(N));i++)
    	{
    		for(j=i+1;j<=(int)(sqrt(N));j++)
    		{
    			if((gcd(i,j)==1)&&((j-i)%2==1))
    			{
    				num = i*i+j*j;
    				k=0;
    				while ((++k) * num <= N)
    					count++;
    			}
    		}
    	}
    	printf("%d\n",count);
    	T--;
    }
    return 0;
}
