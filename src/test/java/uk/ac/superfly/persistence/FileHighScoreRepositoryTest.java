package uk.ac.superfly.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileHighScoreRepositoryTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void missingFileReturnsZero() {
        var repository = repositoryFor("missing.txt");

        assertEquals(0, repository.load());
    }

    @Test
    void validStoredScoreLoadsCorrectly() throws Exception {
        Path scoreFile = temporaryDirectory.resolve("score.txt");
        Files.writeString(scoreFile, "42");

        var repository = new FileHighScoreRepository(scoreFile);

        assertEquals(42, repository.load());
    }

    @Test
    void savingCreatesAndWritesFile() throws Exception {
        Path scoreFile = temporaryDirectory.resolve("score.txt");
        var repository = new FileHighScoreRepository(scoreFile);

        repository.save(17);

        assertTrue(Files.exists(scoreFile));
        assertEquals("17", Files.readString(scoreFile));
    }

    @Test
    void savedScoreCanBeLoadedAgain() {
        var repository = repositoryFor("score.txt");
        repository.save(81);

        var reloadedRepository = repositoryFor("score.txt");

        assertEquals(81, reloadedRepository.load());
    }

    @Test
    void whitespaceAroundStoredNumberIsIgnored() throws Exception {
        Path scoreFile = temporaryDirectory.resolve("score.txt");
        Files.writeString(scoreFile, "  \n  73  \r\n");

        var repository = new FileHighScoreRepository(scoreFile);

        assertEquals(73, repository.load());
    }

    @Test
    void corruptContentRaisesClearException() throws Exception {
        Path scoreFile = temporaryDirectory.resolve("score.txt");
        Files.writeString(scoreFile, "not-a-score");
        var repository = new FileHighScoreRepository(scoreFile);

        IllegalStateException exception = assertThrows(IllegalStateException.class, repository::load);

        assertTrue(exception.getMessage().contains("invalid non-negative integer"));
        assertTrue(exception.getMessage().contains(scoreFile.toAbsolutePath().toString()));
    }

    @Test
    void unexpectedReadProblemIsNotSilentlyIgnored() {
        var repository = new FileHighScoreRepository(temporaryDirectory);

        assertThrows(UncheckedIOException.class, repository::load);
    }

    private FileHighScoreRepository repositoryFor(String fileName) {
        return new FileHighScoreRepository(temporaryDirectory.resolve(fileName));
    }
}
