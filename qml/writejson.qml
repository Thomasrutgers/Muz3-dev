
//export selected notes and lines to the command prompt.
//form: java -cp (dir) invoke ?????

//export lines: placement


import QtQuick 2.0
import MuseScore 3.0
import FileIO 3.0

MuseScore {
  version:  "3.0"
  description: "writejson"
  menuPath: "Plugins.WriteJSON"

  function printElement(e) {
    var keys = Object.keys(e).sort();
    for (var j=0; j<keys.length; j++) if (typeof e[keys[j]] != 'undefined') console.log(keys[j], ':', e[keys[j]]);
  }
  FileIO {
    id: outFile
    source: { tempPath() +"/invoke" + new Date().getTime() + "-out.json" }
  }
  FileIO {
    id: inFile
    source: { tempPath() +"/invoke" + new Date().getTime() + "-in.json" }
  }
  onRun: {

    var data = {};
    data.rules = [];
    data.notes = [];
    var selection = (curScore.selection.elements)
    for (var i=0; i<selection.length; i++) {
      var e = selection[i];
      //printElement(e);
      if (e.type == Element.NOTE) {
        var n = {};
        var m = e.parent.parent; //measure
        n.tick = m.tick;
        if (m.next != null) {
          n.nextTick = m.next.tick
        }
        else n.nextTick = -1;
        n.pitch = e.pitch;
        n.tpc=e.tpc;
        n.track=e.track;
        data.notes.push(n);
      }
      if (e.type == Element.STAFF_TEXT) {
        var r = {};
        r.text = e.text;
        r.startTick = e.parent.parent.firstSegment.tick;
        if (e.parent.parent.nextMeasure != null) {
          r.nextTick=e.parent.parent.nextMeasure.firstSegment.tick;
        }
        else {
          r.nextTick=-1;
        }
        r.track = e.track
        data.rules.push(r);
      }
    }
    var out = JSON.stringify(data);
    console.log("Wrote: outFile2.source");
    outFile.write(out);
  }
}
