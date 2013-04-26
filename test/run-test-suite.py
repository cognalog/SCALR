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

def header_message(message):
  print bcolors.HEADER + message + bcolors.ENDC

def run_test_suite_for(root):
  for directory in listdir(root):
    header_message(directory + "/")
    tscalr = ""
    fscalrs = []
    for file in listdir(join(root,directory)):
      if file.endswith(".tscalr"):
        tscalr = root + "/" + directory + "/" + file + " "
      if file.endswith(".fscalr"):
        fscalrs.append(root + "/" + directory + "/" + file + " ")
    
    a_test = ".././scalr " + tscalr
    for fscalr in fscalrs:
      a_test += fscalr
    print a_test + "\n"

print "===Beginning test suite==="
print ""
print "Starting with tests that should succeed..."

run_test_suite_for("tests-that-should-succeed")

print "Now onto tests that should raise exceptions..."

run_test_suite_for("tests-that-should-raise-exceptions")
