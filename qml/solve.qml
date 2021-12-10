import QtQuick 2.0
import MuseScore 3.0
import FileIO 3.0

MuseScore {
  version:  "0.1.0"
  description: "Muz3 Solve"
  menuPath: "Plugins.Solve"

  FileIO {
    id: outFile
    source: { "c:/temp/invoke" + new Date().getTime() + "-out.json" }
  }

  FileIO {
    id: inFile
    //temppath doesn't work? whats the right way to go?
    //source: { tempPath() +"/invoke" + new Date().getTime() + "-in.json" }
    source: { "c:/temp/invoke" + new Date().getTime() + "-in.json" }
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
      var selection = (curScore.selection.elements)
      for (var i=0; i<selection.length; i++) {
        var e = selection[i];
        if (e.type == Element.NOTE) {
          var n = {};
          var s = e.parent.parent; //segment
          n.tick = s.tick;
          if (s.next != null) {
          n.nextTick = s.next.tick
        }
        else n.nextTick = -1;
        n.headGroup = e.headGroup;
        n.pitch = e.pitch;
        n.tpc=e.tpc;
        cursor.rewindToTick(n.tick);
        n.keySignature=cursor.keySignature;
        n.track=e.track;
        dataOut.notes.push(n);
      }
      if (e.type == Element.STAFF_TEXT) {
        var c = {};
        if (e.subStyle==21) { //normal staff text
          var s = e.parent; //segment
          c.tick = s.tick;
          c.track = e.track
          dataOut.constraints.push(c);
        }
      }
    }
    var outString = JSON.stringify(dataOut);
    console.log("Wrote: outFile2.source");
    outFile.write(outString);
    //homepath??
    //proc.start("java -jar " + outFile.homePath()+"/Documents/MuseScore3/Plugins/invoke.jar "+outFile.source+" "+inFile.source);
    proc.start("java -jar C:/Users/thoma/OneDrive/Documents/MuseScore3/Plugins/invoke.jar "+outFile.source+" "+inFile.source);
    }
    catch (err) {
    console.log(err.message);
    }
  }

  QProcess {
    id: proc
    onFinished: {
      try {
        console.log("Output:");
        console.log(proc.readAllStandardOutput());
        console.log("Data in:");
        var inString = inFile.read();
        var dataIn = JSON.parse(inString);
        console.log(dataIn.naam);
        Qt.quit()
      }
      catch (err) {
        console.log(err.message);
      }
    }
  }
}
