// class name : ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicyBase
// method name: start()
// side       : merge
// region     : lines 45-56 (affected lines: [45, 52, 53, 54, 55, 56])
class TimeBasedFileNamingAndTriggeringPolicyBase {
    void start__() {
    DateTokenConverter<Object> dtc = tbrp.fileNamePattern.getPrimaryDateTokenConverter();
    if (dtc == null) {
      throw new IllegalStateException("FileNamePattern ["
              + tbrp.fileNamePattern.getPattern()
              + "] does not contain a valid DateToken");
    }

    if (dtc.getTimeZone() != null) {
      rc = new RollingCalendar(dtc.getTimeZone(), Locale.getDefault());
    } else {
      rc = new RollingCalendar();
    }
    }
}
