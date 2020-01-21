import QtQuick 2.0
import MuseScore 3.0
import FileIO 3.0
MuseScore {
  FileIO {
    id: outFile
    source: "example.txt"
}
  onRun: {
    try {
      var selection = (curScore.selection.elements)
      for (var i=0; i<selection.length; i++) {
        console.log('------------------------------------------------------------------')
        console.log(selection[i].name.toUpperCase());
        console.log('------------------------------------------------------------------')
        var keys = Object.keys(selection[i]).sort();
        for (var j=0; j<keys.length; j++) {
          var value = selection[i][keys[j]]
          if (typeof value != 'undefined') console.log(keys[j], ':', value);
        }
      }
      outFile.write("test")
    }
    catch (err) {
      console.log(err.message);
    }

  }
}
