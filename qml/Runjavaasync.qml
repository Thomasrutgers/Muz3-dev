
import QtQuick 2.0
import MuseScore 3.0

MuseScore {
      menuPath: "Plugins.run"
      version:  "3.0"
      description: "This demo plugin runs an external command."
      requiresScore: false

      QProcess {
        id: proc
        onFinished: {
           console.log(proc.readAllStandardOutput());
            Qt.quit()
        }
}
      onRun: {
            proc.start("java -cp /home/thomas/eclipse-workspace/test123/bin Test123"); // Linux, Mac(?)
            }
      }
