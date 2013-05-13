import sys
from decimal import Decimal
from midiutil.MidiFile import MIDIFile

class Track:
  def __init__(self):
    self.notes = []

  def __str__(self):
    temp = "t"
    for note in self.notes:
      temp += "[" + str(note) + "]"
    return temp

class Note:
  def __init__(self, pitch, duration, volume):
    self.pitch = pitch
    self.duration = duration
    self.volume = volume

  def __str__(self):
    return "n[" + str(self.pitch) + "," + str(self.duration) + "," + str(self.volume) + "]"

mat1 = sys.argv[1]

tracks = []

trks = mat1.split("][")
for trk in trks:
  temp = Track()
  for note in str(trk).strip('[]').split("|"):
    temp_note = str(note).split(",")
    temp.notes.append(Note(int(temp_note[0]), Decimal(temp_note[1]), int(temp_note[2])))
  tracks.append(temp)


###################################
###### PRINT OUT MIDI STUFF #######
###################################

MyMIDI = MIDIFile(len(temp.notes))

count = 0
for track in tracks:
  track_no = count
  time = 0
  MyMIDI.addTrackName(track_no,time,"Sample Track")
  MyMIDI.addTempo(track_no,time, 120)
  count += 1

  current_duration = 0
  for note in track.notes:
    print "Track number " +str(track_no) + ", Pitch " + str(note.pitch) + ", current_duration " + str(current_duration) + ", duration " +str(note.duration) +", volume " +str(note.volume)
    MyMIDI.addNote(track_no,0,note.pitch,current_duration,note.duration,note.volume)
    current_duration += note.duration

binfile = open("output.mid", 'wb')
MyMIDI.writeFile(binfile)
binfile.close()


