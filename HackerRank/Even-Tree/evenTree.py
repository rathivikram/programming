# Enter your code here. Read input from STDIN. Print output to STDOUT

children = []
count = []
def getCount(u):
    res = 0
    for child in children[u]:
        res += getCount(child)
    count[u] = res+1
    return count[u]
    
def main():
    N,M = map(int,raw_input().split())
    global children
    children = [[] for i in range(0,N+1)]
    global count
    count = [0 for i in range(0,N+1)]
    for i in range(0,M):
        u,v = map(int,raw_input().split())
        children[v].append(u)
    getCount(1)
    res = 0
    for i in range(2,N+1):
        if(count[i] != 0 and count[i]%2==0 ):
            res+=1
    print res
    
    
if __name__ == "__main__":
    main()
