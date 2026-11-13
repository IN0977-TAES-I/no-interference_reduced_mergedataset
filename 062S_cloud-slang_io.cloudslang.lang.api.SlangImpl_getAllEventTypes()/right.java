// class name : io.cloudslang.lang.api.SlangImpl
// method name: getAllEventTypes()
// side       : right
// region     : lines 116-125 (affected lines: [116, 123, 124, 125])
class SlangImpl {
    void getAllEventTypes__() {
        Assert.assertNotNull(compilationArtifact);
        Set<SlangSource> dependencyFiles = new HashSet<>();
        dependencyFiles.add(SlangSource.fromFile(tempDependencyFile));
        Mockito.verify(compiler).compile(tempFile, dependencyFiles);
    }

    @Test(expected = Exception.class)
    public void testCompileOperationWithException() throws IOException {
        Mockito.when(compiler.compile(any(SlangSource.class), anySetOf(SlangSource.class))).thenThrow(Exception.class);
        SlangSource tempFile = createTempFile();
    }
}
