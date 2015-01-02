import os
#Environment Variables
workingDirectory="./"
outputDirectory=workingDirectory+"processed/"
arffOutputDirectory=workingDirectory+"arff_train/"
testDirectory=workingDirectory+"processed_test/"
os.makedirs(outputDirectory)
os.makedirs(testDirectory)
os.makedirs(arffOutputDirectory)
lis=[]

#Used to convert dihong's file format to arff format
def ArffConv(inpFilename):
    fp=open(workingDirectory+"train/"+inpFilename,"r")
    rd=fp.readlines()
    wp=open(arffOutputDirectory+inpFilename+".arff","w")
    wp.write("@RELATION CU\n")
    n=rd[1].strip().split(' ')
    for i in range(1,len(n)):
        wp.write("@ATTRIBUTE %d REAL\n"%(i))
    wp.write("@ATTRIBUTE class {1,2}\n")
    wp.write("@DATA\n")
    for each in rd[1:]:
        w=each.strip().split()
        line=""
        for j in w[1:]:
            line+=j.strip()
            line+=","
        line+=w[0]
        wp.write("%s\n"%(line))
    wp.close()


def featureSelection(feature):
    if feature==0:
        cmd="""java -cp weka.jar weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.SymmetricalUncertAttributeEval "  -i %s   -S "weka.attributeSelection.Ranker -N 200" -o %s"""
        mode="SU"
    elif feature==1:
        cmd="""java -cp weka.jar weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.ChiSquaredAttributeEval "  -i %s   -S "weka.attributeSelection.Ranker -N 200" -o %s"""    
        mode="ChiSquared"
    elif feature==2:
        cmd="""java -cp weka.jar weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.InfoGainAttributeEval "  -i %s   -S "weka.attributeSelection.Ranker -N 200" -o %s"""    
        mode="InfoGain"
    else :
        cmd="""java -cp weka.jar weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.SVMAttributeEval -X 1 -Y 0 -Z 0 -P 1.0E-25 -T 1.0E-10 -C 1.0 -N 2"  -i %s   -S "weka.attributeSelection.Ranker -N 200" -o %s"""
        mode="SVM-RFE"
    for each in lis:
        #cmd="""java -cp weka.jar weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.SymmetricalUncertAttributeEval "  -i /home/sethuraman/work/dataset/processed/%s.arff   -S "weka.attributeSelection.Ranker -N 300" -o /home/sethuraman/work/dataset/reduced_data_SU/%s_reduced.arff"""%(each,each)
        #cmd="""java -cp weka.jar weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.SVMAttributeEval -X 1 -Y 1 -Z 0 -P 1.0E-25 -T 1.0E-10 -C 1.0 -N 2"  -i /home/sethuraman/work/dataset/processed/%s.arff   -S "weka.attributeSelection.Ranker -N 300" -o /home/sethuraman/work/dataset/reduced_data/%s_reduced.arff"""%(each,each)
        os.system(cmd%(arffOutputDirectory+each+".arff",outputDirectory+each+"_reduced.arff"))
        print "reducing %s via %s"%(each,mode)

#"""java -cp weka.jar weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.SVMAttributeEval -X 1 -Y 1 -Z 0 -P 1.0E-25 -T 1.0E-10 -C 1.0 -N 2"  -i /home/sethuraman/work/dataset/processed/%s.arff   -S "weka.attributeSelection.Ranker -N 300" -o /home/sethuraman/work/dataset/reduced_data/%s_reduced.arff"""%(each,each)


def dihong_conv(inpFile):
  inpDir="/home/sethuraman/work/dataset/reduced_data_SU/"
  fp=open(inpDir+inpFile,"r")
  rd=fp.readlines()
  i=0
  line=[]
  while rd[i].find("@data")==-1 :
    i+=1
  i+=1
  while i<len(rd):
    if len(rd[i].strip())>2:
      line+=[rd[i].strip()]
    i+=1
  fp.close()
  wfp=open("/home/sethuraman/work/dataset/dihong_out/"+inpFile,"w+")
  w=line[1].split(',')
  wfp.write("%d %d\n"%(len(w)-1,len(line)))
  for each in line:
    wrt=""
    w=each.split(',')
    wrt+=w[-1]
    for j in w[:-1]:
      wrt+=" %s"%j
    wfp.write("%s\n"%(wrt))
  wfp.close()

#Reduces the test data to the feature selection used in the training samples
def test_data_reducer(inpTrain,inpTest):
    fp=open(outputDirectory+inpTrain+"_reduced.arff","r")
    fp2=open(workingDirectory+"test/"+inpTest,"r")
    fp3=open(testDirectory+inpTrain+"_test.arff","w+")
    rd=fp.readlines()
    fp.close()
    i=0
    index=[]
    i=0
    while rd[i].lower().find("@data")==-1 :
      if(rd[i].lower().find("@attribute")!=-1):
         spl=rd[i].split(" ")
         if(spl[1].find('class')==-1):
            index+=[int(spl[1])]
      i+=1
    fp3.write("@relation temp-test\n")
    i=1
    while(rd[i].find("@data")==-1):
      fp3.write(rd[i])
      i+=1
    fp3.write("@data\n")
    rd=[]
    rd=fp2.readlines()
    i=1
    fp2.close()
    while i<len(rd):
      if(rd[i].strip()!=""):
         line=""
         spl=rd[i].split(" ")
         j=0
         while j<len(index):
           line+=spl[index[j]]
           line+=","
           j+=1
         line+=spl[0]
         fp3.write(line+"\n")
      i+=1
    fp3.close()

def run_classifier_NB(inpFile,counter,direc):
    cmd="""java -cp weka.jar weka.classifiers.bayes.NaiveBayes   -t %s -T %s  -v  -i > %sNB/output-%d.txt"""%(outputDirectory+inpFile+"_reduced.arff",testDirectory+inpFile+"_test.arff",direc,counter)
    os.system(cmd)
    #java -cp weka.jar  weka.classifiers.bayes.NaiveBayes -t /home/sethuraman/work/dataset/reduced_data/1.train_reduced.arff -T /home/sethuraman/work/dataset/processed_test/1.train_reduced.arff_test.arff  

def run_classifier_SMO(inpFile,counter,direc):
    cmd="""java -cp weka.jar weka.classifiers.functions.SMO    -C 1.0 -L 0.001 -P 1.0E-12 -N 2 -V -1 -W 1  -t %s -T %s -v -i > %sSMO/output-%d.txt"""%(outputDirectory+inpFile+"_reduced.arff",testDirectory+inpFile+"_test.arff",direc,counter)
    os.system(cmd)
    #java -cp weka.jar  weka.classifiers.bayes.NaiveBayes -t /home/sethuraman/work/dataset/reduced_data/1.train_reduced.arff -T /home/sethuraman/work/dataset/processed_test/1.train_reduced.arff_test.arff  

def run_classifier_IBk(inpFile,counter,direc,k):
    cmd="""java -cp weka.jar weka.classifiers.lazy.IBk -K %d    -t %s -T %s -v  -i > %sIBk-%d/output-%d.txt"""%(k,outputDirectory+inpFile+"_reduced.arff",testDirectory+inpFile+"_test.arff",direc,k,counter)
    os.system(cmd)
    #java -cp weka.jar  weka.classifiers.bayes.NaiveBayes -t /home/sethuraman/work/dataset/reduced_data/1.train_reduced.arff -T /home/sethuraman/work/dataset/processed_test/1.train_reduced.arff_test.arff  

def run_classifier_J48(inpFile,counter,direc):
    cmd="""java -cp weka.jar weka.classifiers.trees.J48   -t %s -T %s -v  -i > %sJ48/output-%d.txt"""%(outputDirectory+inpFile+"_reduced.arff",testDirectory+inpFile+"_test.arff",direc,counter)
    os.system(cmd)
    #java -cp weka.jar  weka.classifiers. -t /home/sethuraman/work/dataset/reduced_data/1.train_reduced.arff -T /home/sethuraman/work/dataset/processed_test/1.train_reduced.arff_test.arff  

def stat(counter,direc):
    fp=open("%s/output-%d.txt"%(direc,counter),"r")
    rd=fp.readlines()
    fp.close()
    line=[]
    for each in rd:
        if(each.find("Correctly Classified Instance")!=-1):
            line+=[each]
    try:
        os.remove("output.txt")
    except OSError:
        pass
    return float(line[-1].strip().split()[-2])

if __name__=='__main__':
    lis=os.listdir(workingDirectory+"train/")
    lis.sort()
    print "Converting training files to arff files"
    for each in lis:
        ArffConv(each)
    select=0
    fselec=["SU","ChiSquared","InfoGain","SVM-RFE"]
    while select<4 :
       featureSelection(select)
       print "Reducing test files to feature selection"
       lisDotTest=os.listdir(workingDirectory+"test/")
       lisDotTest.sort()
       if len(lisDotTest)!=len(lis):
            print "Mismatch in number of train and test files"
            exit(1)
       i=0
       while i<len(lis):
            test_data_reducer(lis[i],lisDotTest[i])
            i+=1
       accuracy=0.0
       counter=0
       os.makedirs(fselec[select]+"NB")
       for each in lis :
          run_classifier_NB(each,counter,fselec[select])
          accuracy+=stat(counter,fselec[select]+"NB")
          counter+=1
       print "The accuracy of Naive Bayes experiment is %f"%(accuracy/len(lis))
       accuracy=0.0
       os.makedirs(fselec[select]+"IBk-1")
       for each in lis :
           run_classifier_IBk(each,counter,fselec[select],1)
           accuracy+=stat(counter,fselec[select]+"IBk-1")
           counter+=1
       print "The accuracy of KNN k=1 experiment is %f"%(accuracy/len(lis))
       counter=0
       accuracy=0.0
       os.makedirs(fselec[select]+"IBk-5")
       for each in lis :
           run_classifier_IBk(each,counter,fselec[select],5)
           accuracy+=stat(counter,fselec[select]+"IBk-5")
           counter+=1
       counter=0
       print "The accuracy of KNN k=5 experiment is %f"%(accuracy/len(lis))
       os.makedirs(fselec[select]+"IBk-10")
       accuracy=10
       for each in lis :
           run_classifier_IBk(each,counter,fselec[select],10)
           accuracy+=stat(counter,fselec[select]+"IBk-10")
           counter+=1
       counter=0
       print "The accuracy of KNN k=10 experiment is %f"%(accuracy/len(lis))
       os.makedirs(fselec[select]+"J48")
       accuracy=0.0
       for each in lis :
           run_classifier_J48(each,counter,fselec[select])
           accuracy+=stat(counter,fselec[select]+"J48")
           counter+=1
       print "The accuracy of J48 experiment is %f"%(accuracy/len(lis))
       accuracy=0.0
       counter=0
       os.makedirs(fselec[select]+"SMO")
       for each in lis :
           run_classifier_SMO(each,counter,fselec[select])
           accuracy+=stat(counter,fselec[select]+"SMO")
           counter+=1
       print "The accuracy of SMO experiment is %f"%(accuracy/len(lis))
       select+=1
