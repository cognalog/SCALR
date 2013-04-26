############################################################################
# A sample program to create a single-track MIDI file, add a note,
# and write to disk.
############################################################################

#Import the library
from midiutil.MidiFile import MIDIFile

# Create the MIDIFile Object
MyMIDI = MIDIFile(3)

# Add track name and tempo. The first argument to addTrackName and
# addTempo is the time to write the event.
track = 0
time = 0
MyMIDI.addTrackName(track,time,"Sample Track")
MyMIDI.addTempo(track,time, 120)

track2 = 1
time = 0
MyMIDI.addTrackName(track2,time,"Sample Track")
MyMIDI.addTempo(track2,time, 120)

track3 = 1
time = 0
MyMIDI.addTrackName(track2,time,"Sample Track")
MyMIDI.addTempo(track2,time, 120)

# Add a note. addNote expects the following information:
channel = 0
pitch = 60
duration = 1
volume = 100

# Now add the note.
MyMIDI.addNote(track,channel,pitch,time,duration,volume)

# Add a note. addNote expects the following information:
channel = 0
pitch = 64
duration = 1
volume = 100

# Now add the note.
MyMIDI.addNote(track2,channel,pitch,time,duration,volume)

channel = 0
pitch = 62
duration = 1
volume = 100

# Now add the note.
MyMIDI.addNote(track,channel,pitch,1,duration,volume)

channel = 0
pitch = 65
duration = 1
volume = 100

# Now add the note.
MyMIDI.addNote(track2,channel,pitch,1,duration,volume)

channel = 0
pitch = 64
duration = 1
volume = 100

# Now add the note.
MyMIDI.addNote(track,channel,pitch,2,duration,volume)

channel = 0
pitch = 67
duration = 1
volume = 100

# Now add the note.
MyMIDI.addNote(track2,channel,pitch,2,duration,volume)

channel = 0
pitch = 69
duration = 1
volume = 100

# Now add the note.
MyMIDI.addNote(track3,channel,pitch,2,duration,volume)

# And write it to disk.
binfile = open("output.mid", 'wb')
MyMIDI.writeFile(binfile)
binfile.close()

