import QtQuick 2.0
import MuseScore 3.0
import FileIO 3.0

MuseScore {
  version:  "0.1.0"
  description: "Muz3 Solve"
  menuPath: "Plugins.Solve"
  
  property var selection: [];
  
  FileIO {
    id: outFile
    source: { this.tempPath() + "/muz3" + new Date().getTime() + "-out.json" }
  }

  FileIO {
    id: inFile
    //temppath doesn't work? whats the right way to go?
    //source: { tempPath() +"/invoke" + new Date().getTime() + "-in.json" }
    source: { this.tempPath() + "/muz3" + new Date().getTime() + "-in.json" }
  }

  onRun: {
    try {
      var dataOut = {};
      dataOut.constraints = [];
      dataOut.notes = [];
      if (!curScore) Qt.quit();
      var cursor = curScore.newCursor();
      cursor.voice    = 0;
      cursor.staffIdx = 0;
      cursor.rewind(Cursor.SCORE_START);
      //iterate selection
      for (var i=0; i<curScore.selection.elements.length; i++) {
        var e =curScore.selection.elements[i];
        selection.push(e); //store for later in a separate array
        //get notes
        if (e.type == Element.NOTE) {
          var n = {};
          var s = e.parent.parent; //segment
          n.tick = s.tick;
          if (s.next != null) {
            n.nextTick = s.next.tick
          }
          else n.nextTick = -1;
        if (e.headGroup == 0) n.fixed = true;
        else n.fixed = false;
        n.pitch = e.pitch;
        n.tpc=e.tpc;
        cursor.rewindToTick(n.tick);
        n.keySignature=cursor.keySignature;
        n.track=e.track;
        n.selectionIndex = i;
        dataOut.notes.push(n);
        curScore.startCmd();
        e.color="#ff0000";
        curScore.endCmd();
        console.log('--');
          console.log(e.pitch);
      }
      //get text
      if (e.type == Element.STAFF_TEXT) {
        var c = {};
        if (e.subStyle==21) { //normal staff text
          var s = e.parent; //segment
          c.tick = s.tick;
          c.track = e.track
          c.constraintText = e.text;
          c.selectionIndex = i;
          dataOut.constraints.push(c);
        }
      }
    }
    var outString = JSON.stringify(dataOut);
    console.log("Exporting notes....");
    outFile.write(outString);
    //homepath??
    //proc.start("java -jar " + outFile.homePath()+"/Documents/MuseScore3/Plugins/invoke.jar "+outFile.source+" "+inFile.source);

    console.log("java -jar \""+this.filePath+"/Muz3.jar\" "+outFile.source+" "+inFile.source);
    proc.start("java -jar \""+this.filePath+"/Muz3.jar\" "+outFile.source+" "+inFile.source);
  
    }
    catch (err) {
    console.log(err.message);
    }
  }

  QProcess {
    id: proc
    onFinished: {



      try {
        console.log(proc.readAllStandardOutput());
        var inString = inFile.read();
        var dataIn = JSON.parse(inString);
        //noten matchen.
        //geen rekening houden met akkoorden
        //var selection = (curScore.selection.elements);
        var cursor = curScore.newCursor();
        cursor.voice    = 0;
        cursor.staffIdx = 0;
        curScore.startCmd();
        //make notes black again
        for (var i=0; i< selection.length;i++)
        {
        var e = selection[i];
          if (e != null) {
          if (e.type == Element.NOTE) {
            e.color = "000000";
          }
          }
        
        }
        //change notes based on selectionIndex
        for(var i = 0; i < dataIn.notes.length; i++) {        
          var e = selection[dataIn.notes[i].selectionIndex];
          if (e != null) {
          if (e.type == Element.NOTE) {
          e.pitch = dataIn.notes[i].pitch;
          e.tpc1 = dataIn.notes[i].tpc;
          e.tpc2 = dataIn.notes[i].tpc;
          console.log('--');
          console.log(e.pitch);
          }
          }
        }                    
        curScore.endCmd();
        Qt.quit();
      }
    
    catch (err) {
      console.log(err.message);
    }    
 
  }
  }
  

  }