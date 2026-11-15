class TimeBasedFileNamingAndTriggeringPolicyBase {
    TimeBasedRollingPolicy<E> tbrp;
    RollingCalendar rc;
    Date dateInCurrentPeriod;

    public void start() {
      DateTokenConverter dtc = tbrp.fileNamePattern.getPrimaryDateTokenConverter();
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

      setDateInCurrentPeriod(new Date(getCurrentTime()));
      if (tbrp.getParentsRawFileProperty() != null) {
        File currentFile = new File(tbrp.getParentsRawFileProperty());
        if (currentFile.exists() && currentFile.canRead()) {
          setDateInCurrentPeriod(new Date(currentFile.lastModified()));
        }
      }

      addInfo("Setting initial period to " + dateInCurrentPeriod);
      computeNextCheck();
    }
}
