import QtQuick 2.0
import MuseScore 3.0

MuseScore {
  onRun: {
    try {
      var selection = (curScore.selection.elements)
      for (var i=0; i<selection.length; i++) {
        console.log('------------------------------------------------------------------')
        console.log(selection[i].name.toUpperCase());
        console.log('------------------------------------------------------------------')
        var keys = Object.keys(selection[i]);
        for (var j=0; j<keys.length; j++) {
          var value = selection[i][keys[j]]
          if (typeof value != 'undefined') console.log(keys[j], ':', value);
        }
      }
    }
    catch (err) {
      console.log(err.message);
    }

  }
}
