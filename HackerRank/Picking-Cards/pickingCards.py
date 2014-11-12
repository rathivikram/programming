# Enter your code here. Read input from STDIN. Print output to STDOUT

T = int(raw_input())
for k in range(0,T):
    N = int(raw_input())
    count = [0 for i in range(0,N+1)]
    #cards = [0 for i in range(0,N+1)]
    a = map(int,raw_input().split())
    flag = 0
    for num in a:
        if num >= N:
            flag = 1
            break
        count[num] += 1
    if flag == 1:
        print 0 
        continue
    i = 1
    #cards[0] = count[0]
    while i<=N:
        count[i] += count[i-1]
        i+=1
    #print count
    i=1
    res = count[0]
    while i<N:
        res *= (count[i]-i)
        i+=1
    print res%1000000007
