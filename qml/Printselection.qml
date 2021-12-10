import QtQuick 2.0
import MuseScore 3.0

MuseScore {
  onRun: {
    try {
      var selection = (curScore.selection.elements)
      for (var i=0; i<selection.length; i++) {
        //var obj = selection[i];
        //var obj = selection[i].parent;
        var obj = selection[i].parent.parent;
        
        console.log('------------------------------------------------------------------')
        console.log(obj.name.toUpperCase());
        console.log('------------------------------------------------------------------')
        var keys = Object.keys(obj).sort();
        for (var j=0; j<keys.length; j++) {
          var value = obj[keys[j]]
          if (typeof value != 'undefined') console.log(keys[j], ':', value);
        }
      }
    }
    catch (err) {
      console.log(err.message);
    }

  }
}
