# Enter your code here. Read input from STDIN. Print output to STDOUT
T = int(raw_input())

for i in range(0,T):
    N = int(raw_input())
    s = raw_input()
    letters = []
    for c in s:
        letters.append(c)
    letters.sort(reverse =  True)
    #print letters
    words = []
    for c in letters:
        index = 0
        length = len(words)
        while index < length:
            words.append(c + words[index])
            index += 1
        words.append(c)
        #print  words
    for word in words[::-1]:
        print word
