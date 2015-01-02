n=int(raw_input("please enter the value of N:"))
while(n%2==1):
        n=int(raw_input("Odd numbers not applicable, please enter an even number:"))
k=n/2
#keeps track of number of 1's in a row.. definition: (row_num,k) targets equal number of ones in each row
row=[[i,k] for i in range(0,2*k)]
col=[]
i=0
for i in range(0,k):
        col+=[i,2*k-i]

#matrix definition 2k*2k order
a=[[0]*(2*k) for i in range(0,2*k)]
i=0

#constructing the matrix fill column by column


while i<len(col):
        poi=dict()
        #dictionary keeps track of which rows were filled for a corresponding column
        for d in range(0,2*k):
            poi[d]=1
        curr=0
        while row[curr][0]!=i:
                curr+=1
        if(row[curr][-1]>0) and col[i]>0:
            a[i][i]=1
            col[i]-=1
            row[curr][-1]-=1
            del poi[i]
        curr=0
        while row[curr][0]!=2*k-1-i:
                curr+=1
        if(row[curr][-1]>0) and col[i]>0:
            a[2*k-1-i][i]=1
            col[i]-=1
            row[curr][-1]-=1
            del poi[2*k-i-1]
            
        while(col[i]>0):
            # choose the row with maximum availability
            row.sort(key=lambda ro:ro[-1],reverse=True)
            curr=0
            #check if that row is already filled for this col[i]. If yes find next maximal avaialable row
            while curr<len(row):
                if poi.has_key(row[curr][0]) and row[curr][-1]>0:
                    break
                curr+=1
            a[row[curr][0]][i]=1
            col[i]-=1
            row[curr][-1]-=1
            # if a row is used remove it from the dictionary
            del poi[row[curr][0]]
        i+=1

for i in range(0,2*k):
        for j in range(0,2*k):
                if(a[i][j]==1):
                        print 'S',
                else:
                        print 'N',
        print ""
                
