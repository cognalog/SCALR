from os import listdir
from os.path import isfile, join

class bcolors:
    HEADER = '\033[95m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'

    def disable(self):
        self.HEADER = ''
        self.OKGREEN = ''
        self.WARNING = ''
        self.FAIL = ''
        self.ENDC = ''

def succeed_message(message):
  print bcolors.OKGREEN + message + bcolors.ENDC

def fail_message(message):
  print bcolors.OKGREEN + message + bcolors.ENDC

print "===Beginning test suite==="
print ""
print "Starting with tests that should succeed..."

root="tests-that-should-succeed"
for directory in listdir(root):
  print directory + "/"
  for file in listdir(join(root,directory)):
    print "\t" + file

print "Now onto tests that should raise exceptions..."

root="tests-that-should-raise-exceptions"
for directory in listdir(root):
  print directory + "/"
  for file in listdir(join(root,directory)):
    print "\t" + file
