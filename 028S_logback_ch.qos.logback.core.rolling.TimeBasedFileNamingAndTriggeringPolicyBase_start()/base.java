// class name : ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicyBase
// method name: start()
// side       : base
// region     : lines 45-56 (affected lines: [45, 52, 53, 54, 55, 56])
class TimeBasedFileNamingAndTriggeringPolicyBase {
    void start__() {
    if (dtc == null) {
      throw new IllegalStateException("FileNamePattern ["
              + tbrp.fileNamePattern.getPattern()
              + "] does not contain a valid DateToken");
    }

    rc = new RollingCalendar();
    rc.init(dtc.getDatePattern());
    addInfo("The date pattern is '" + dtc.getDatePattern()
            + "' from file name pattern '" + tbrp.fileNamePattern.getPattern()
            + "'.");
    rc.printPeriodicity(this);
    }
}
