import os
import sys
rootdir = sys.argv[1]

with open('output.txt','a') as fout:
  for root, subFolders, files in os.walk(rootdir):
    for file in files:
      with open(os.path.join(root, file), 'r') as fin:
        if(not(str(os.path.join(root, file)).endswith(".class"))):
          fout.write("********\n")
          fout.write(str(os.path.join(root, file)) + "\n")
          for lines in fin:
            fout.write(lines)
          fout.write("\n")
fout.close()